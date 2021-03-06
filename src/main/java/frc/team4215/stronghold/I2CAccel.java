package frc.team4215.stronghold;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.I2C;

public class I2CAccel {
    
    private static I2C accel;
    private static final double coeff = .061;
    private static boolean pingFlag;
    private final static byte WHO_AM_I = 0x0F, CTRL_REG = 0x1F, OUT_REG = 0x28,
            FIFO_SRC_REG = 0x2F;
    private static final double G_IN_IPS2 = 386.09;
    private static byte[] buffL = new byte[1], buffH = new byte[1],
            ID = new byte[1];
    private static double[] accelVal = new double[3];
    private static double[] velocVal = new double[3];
    private static double[] positVal = new double[3];
    
    private static final double deltat = .08;
    
    private static Thread pingerThread;

    /**
     * Should always size it as [x][3].
     */
    protected static double[][] velocity;
    protected static double[] position;
    
    public static void initAccel() {
        accel = new I2C(I2C.Port.kOnboard, 0x1D);
        // Accelerometer
        byte[] reg12 = new byte[1];
        accel.read(0x12, 1, reg12);

        accel.write(CTRL_REG + 1, 0x57); // 0x20
        accel.write(CTRL_REG + 3, 0x00); // 0x22
        accel.write(CTRL_REG + 4, 0x00); // 0x23
        accel.write(CTRL_REG + 5, 0x40); // 0x24
        accel.write(CTRL_REG + 6, 0x00); // 0x25
        accel.write(CTRL_REG + 7, 0x00); // 0x26
        accel.read(WHO_AM_I, 1, ID);
        if (ID[0] == 0x49) RobotModule.logger.info("Accelerometer enabled!");
        else RobotModule.logger.warn("Accelerometer disabled!");
        
    }

    public static void velInteg() {
        accel.read(I2CAccel.FIFO_SRC_REG, 1, buffL);
        double[] acc = new double[3];
        int loopCount = buffL[0] & 0x1f;
        ArrayList<double[]> accelList = new ArrayList<double[]>();
        for (int i = 0; i < loopCount; i++) {
            pingAccel();
            accelList.add(accelVal);
        }

        for (int i = 0; i < loopCount; i++) {
            double[] cur = accelList.get(i);
            for (int j = 0; j < 3; j++)
                acc[j] += cur[j];
        }
        for (int j = 0; j < 3; j++)
            velocVal[j] += acc[j] * deltat;
        
        // Then get the current position
        distInteg();
    }

    public static void distInteg() {
        for (int j = 0; j < 3; j++)
            positVal[j] += velocVal[j] * deltat;
    }
    
    /**
     * @return a copy of accelVal.
     */
    public static void pingAccel() {
        
        accel.read(OUT_REG, 1, buffL);
        accel.read(OUT_REG + 1, 1, buffH);
        accelVal[0] = concatCorrect(buffH[0], buffL[0]);
        accelVal[0] /= 1000;
        accelVal[0] *= G_IN_IPS2;

        accel.read(OUT_REG + 2, 1, buffL);
        accel.read(OUT_REG + 3, 1, buffH);
        accelVal[1] = concatCorrect(buffH[0], buffL[0]);
        accelVal[1] /= 1000;
        accelVal[1] *= G_IN_IPS2;

        accel.read(OUT_REG + 4, 1, buffL);
        accel.read(OUT_REG + 5, 1, buffH);
        accelVal[2] = concatCorrect(buffH[0], buffL[0]);
        accelVal[2] /= 1000;
        accelVal[2] *= G_IN_IPS2;
        RobotModule.logger.info("Accel: " + accelVal[1]);
    }
    
    public static double concatCorrect(byte h, byte l) {
        int high = Byte.toUnsignedInt(h);
        int low = Byte.toUnsignedInt(l);
        int test = ((0xFF & high) << 8) + (0xFF & low);

        test = ((test > 0x7FFF) ? test - 0xFFFF : test);
        test *= coeff;

        if (test > 65) return test;
        else return 0;
    }
    
    public static void pingerStart() {
        Runnable pinger = () -> {
            while (pingFlag) {
                velInteg();
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        
        pingerThread = new Thread(pinger);
        pingFlag = true;
        pingerThread.start();
        
    }
    
    public static double[] getAccel() {
        return accelVal;
    }

    public static double[] getVeloc() {
        return velocVal;
    }

    public static void pingerStop() {
        pingFlag = false;
    }
    
}
