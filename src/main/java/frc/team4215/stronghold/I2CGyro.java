package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class I2CGyro {

    private static I2C gyro;
    private static boolean pingFlag;
    private static double lastTime = 0;
    private static double coeff = 0.00875;
    private static HardwareTimer hardTimer = new HardwareTimer();
    private static Timer.Interface timer;
    
    private final static byte WHO_AM_I = 0x0F, CTRL_REG = 0x20,
            OUT_REG = 0x28;

    private static double[] angles;
    private static int limit = 15;
    private static byte[] ID = new byte[1], dataBuffer = new byte[1];
    
    private static Thread threadPing;

    public static void initGyro() {

        // Instantiating the gyro Object
        gyro = new I2C(I2C.Port.kOnboard, 0x1D);

        /*
         * Setting up the Gyro references are on the Github wiki
         */
        
        gyro.write(CTRL_REG, 0x3F);
        gyro.write(CTRL_REG + 1, 0x00);
        gyro.write(CTRL_REG + 2, 0x00);
        gyro.write(CTRL_REG + 3, 0x00);
        gyro.write(CTRL_REG + 4, 0x00);

        boolean worked = gyro.read(WHO_AM_I, 1, ID);

        if (ID[0] == 0xD4 && worked) {
            RobotModule.logger.info("Gyro active!");
        } else {
            RobotModule.logger.error("Gyro not operating, please check wiring! "
                            	+ Integer.toBinaryString(ID[0]) + " "
                            	+ worked);
        }

        /*
         * The gyroscope only gives us angular velocity so we need to
         * integrate it.
         */
        timer = hardTimer.newTimer();

        // Resetting the tracker variables
        angles = new double[] { 0, 0, 0 };
    }

    public static void pingGyro() {

        double newTime = timer.get();
        double deltat = (newTime - lastTime);
        lastTime = newTime;
        double[] angularSpeed = new double[3];

        /*
         * The velocities are stored in registers 0x28-0x2D. And are
         * stored in two's complement form with the first byte being
         * the right half of the number and the second being the left
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
        RobotModule.logger.info("Angle Speed: " + angularSpeed[2]);
        double cX, cY, cZ;
        cX = angles[0] + angularSpeed[0] * deltat;
        cY = angles[1] + angularSpeed[1] * deltat;
        cZ = angles[2] + angularSpeed[2] * deltat;
        
        cX = cX % 360;
        if (cX < 0) cX += 360;
        
        cY = cY % 360;
        if (cY < 0) cY += 360;
        
        cZ = cZ % 360;
        if (cZ < 0) cZ += 360;

        angles = new double[] { cX, cY, cZ };
    }
    /**
     * 
     */		
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
    
    public static void pingerStop(){
    	pingFlag = false;
    }
    
    private static double concatCorrect(byte h, byte l) {
        int high = Byte.toUnsignedInt(h);
        int low = Byte.toUnsignedInt(l);
        int test = ((0xFF & high) << 8) + (0xFF & low);
        test = (test > 0x7FFF) ? test - 0xFFFF : test;
        double testTwo = (coeff*test)/20;
        
        // Makes sure that any offset is eliminated
        if(Math.abs(testTwo) > limit) 
        	return testTwo;
        else
        	return 0;
    }

    /**
     * Gives the current angular position of the robot Returns a array
     * of three doubles
     *
     * @return angles
     */
    public static double[] getAngles() {
        return angles;
    }

}
