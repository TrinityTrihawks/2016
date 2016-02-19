package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class I2CGyro {

    private static I2C gyro;
    private static boolean pingFlag;
    private static double lastTime = 0;
    private static int bitMax = 0xFFFF;
    private static int range = 200;
    private static double coeff = (double) range / bitMax;
    private static HardwareTimer hardTimer = new HardwareTimer();
    private static Timer.Interface timer;
    
    private final static byte WHO_AM_I = 0x0F, CTRL_REG = 0x20,
            OUT_REG = 0x28;

    private static double[] angles;
    private static double offsetX, offsetY, offsetZ;
    private static int numOfCalibPings = 1000;
    private static byte[] ID = new byte[1], dataBuffer = new byte[1];
    
    private static Thread threadPing;

    public static void initGyro() {

        // Instantiating the gyro Object
        gyro = new I2C(I2C.Port.kOnboard, 0x1D);

        // Checking if this gyro actually exists;

        gyro.write(CTRL_REG, 0x3F);
        gyro.write(CTRL_REG + 1, 0x00);
        gyro.write(CTRL_REG + 2, 0x00);
        gyro.write(CTRL_REG + 3, 0x00);
        gyro.write(CTRL_REG + 4, 0x00);

        boolean worked = gyro.read(WHO_AM_I, 1, ID);
        ID[0] = normalize(ID[0]);

        if (ID[0] == 0xD4) {
            RobotModule.logger.info("Gyro active!");
        } else {
            RobotModule.logger
                    .error("Gyro not operating, please check wiring! "
                            + Integer.toBinaryString(ID[0]) + " "
                            + worked);
        }

        /*
         * So, without explanation the following code is going to seem
         * a bit cryptic. Essentially the Gyro is set up with a series
         * of bits(1's or 0's) and these base 16 numbers correspond to
         * the particular series of bits you need. the numbers and
         * such are found at *so and so*
         */

        /*
         * The gyroscope only gives us angular velocity so we need to
         * integrate it.
         */
        timer = hardTimer.newTimer();

        // Resetting the tracker varibles
        angles = new double[] { 0, 0, 0 };
    }

    public static void pingGyro() {

        double newTime = timer.get();
        double deltat = newTime - lastTime;
        lastTime = newTime;
        int[] angularSpeed = new int[3];

        /*
         * The velocities are stored in registers 0x28-0x2D. And are
         * stored in two's complement form with the first byte being
         * the left half of the number and the second being the right
         * half.
         */
        gyro.read(OUT_REG, 1, dataBuffer);
        byte gL = dataBuffer[0];
        gyro.read(OUT_REG + 1, 1, dataBuffer);
        byte gH = dataBuffer[0];
        angularSpeed[0] = concatCorrect(gL, gH);

        gyro.read(OUT_REG + 2, 1, dataBuffer);
        gL = dataBuffer[0];
        gyro.read(OUT_REG + 3, 1, dataBuffer);
        gH = dataBuffer[0];
        angularSpeed[1] = concatCorrect(gL, gH);

        gyro.read(OUT_REG + 4, 1, dataBuffer);
        gL = dataBuffer[0];
        gyro.read(OUT_REG + 5, 1, dataBuffer);
        gH = dataBuffer[0];
        angularSpeed[2] = concatCorrect(gL, gH);

        double cX, cY, cZ;
        cX = angles[0] + (coeff * angularSpeed[0] - offsetX) * deltat;
        cY = angles[1] + (coeff * angularSpeed[1] - offsetY) * deltat;
        cZ = angles[2] + (coeff * angularSpeed[2] - offsetZ) * deltat;
        cX = cX % 360;
        if (cX < 0) cX += 360;
        cY = cY % 360;
        if (cY < 0) cY += 360;
        cZ = cZ % 360;
        if (cZ < 0) cZ += 360;

        angles = new double[] { cX, cY, cZ };
    }

    public static void pingerStart() {
        // Starts a thread to continually update our status variables
        Runnable pinger = () -> {
            while (pingFlag) {
                pingGyro();
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadPing = new Thread(pinger);
        pingFlag = true;
        timer.start();

        threadPing.start();
    }

    public static byte normalize(byte in) {
        return (byte) ((byte) 0x00FF & in);
    }
    
    public static int concatCorrect(byte h, byte l) {
        int high = Byte.toUnsignedInt(h);
        int low = Byte.toUnsignedInt(l);
        int test = ((0xFF & high) << 8) + (0xFF & low);
        
        return (test > 0x1FFFF) ? test - 0x10000 : test;
    }
    
    public static void calibrate() {

        int[] angularSpeed = new int[3];
        double totalSumX = 0;
        double totalSumY = 0;
        double totalSumZ = 0;

        for (int i = 0; i < numOfCalibPings; i++) {
            gyro.read(OUT_REG, 1, dataBuffer);
            byte gL = dataBuffer[0];
            gyro.read(OUT_REG + 1, 1, dataBuffer);
            byte gH = dataBuffer[0];
            angularSpeed[0] = concatCorrect(gL, gH);

            gyro.read(OUT_REG + 2, 1, dataBuffer);
            gL = dataBuffer[0];
            gyro.read(OUT_REG + 3, 1, dataBuffer);
            gH = dataBuffer[0];
            angularSpeed[1] = concatCorrect(gL, gH);

            gyro.read(OUT_REG + 4, 1, dataBuffer);
            gL = dataBuffer[0];
            gyro.read(OUT_REG + 5, 1, dataBuffer);
            gH = dataBuffer[0];
            angularSpeed[2] = concatCorrect(gL, gH);

            totalSumX += coeff * angularSpeed[0];
            totalSumY += coeff * angularSpeed[1];
            totalSumZ += coeff * angularSpeed[2];
        }

        offsetX = totalSumX / numOfCalibPings;
        offsetY = totalSumY / numOfCalibPings;
        offsetZ = totalSumZ / numOfCalibPings;

        RobotModule.logger.info("Offset : " + offsetX + " ," + offsetY
                + " ," + offsetZ);
        RobotModule.logger.info("Coeffecient : " + coeff);
    }

    /**
     * Gives the current angular position of the robot Returns a array
     * of three doubles
     *
     * @return
     */
    public static double[] getAngles() {
        return angles;
    }

}
