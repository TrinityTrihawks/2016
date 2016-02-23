package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.I2C;

public class I2CAccel {

    private static I2C accel;
    private static double coeff = .061;
    private static boolean pingFlag;
    private final static byte WHO_AM_I = 0x0F, CTRL_REG = 0x1F,
            OUT_REG = 0x28;
    private static double G_IN_IPS2 = 386.09;
    private static byte[] buffL = new byte[1], buffH = new byte[1],
            ID = new byte[1];
    private static double[] accelVal = new double[3];
    private static Thread pingerThread;

    public static void initAccel() {
        accel = new I2C(I2C.Port.kOnboard, 0x1D);
        // Accelerometer
        byte[] reg12 = new byte[1];
        accel.read(0x12, 1, reg12);
        
        accel.write(CTRL_REG + 1, 0x57); // 0x20
        accel.write(CTRL_REG + 3, 0x0);// 4); //0x22
        accel.write(CTRL_REG + 4, 0x0);// 4); //0x23
        accel.write(CTRL_REG + 6, 0x00); // 0x25
        accel.write(CTRL_REG + 7, 0x00); // 0x26
        accel.read(WHO_AM_I, 1, ID);
    }

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
        RobotModule.logger.info("Accel: " + accelVal[1] );

    }

    public static double concatCorrect(byte h, byte l) {
        int high = Byte.toUnsignedInt(h);
        int low = Byte.toUnsignedInt(l);
        int test = ((0xFF & high) << 8) + (0xFF & low);
        
        test = ((test > 0x7FFF) ? test - 0xFFFF : test);
        test *= coeff;
        
        if(test > 65)
        	return test;
        else
        	return 0;
    }

    public static void pingerStart() {
        Runnable pinger = () -> {
            while (pingFlag) {
                pingAccel();
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

    public static void pingerStop() {
        pingFlag = false;
    }

}
