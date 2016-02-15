package frc.team4215.stronghold;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class I2CAccel {
	static I2C accel;
	
	static boolean pingFlag;
	final static byte WHO_AM_I = 0x0F,
			  CTRL_REG = 0x1F,
			  OUT_REG = 0x28;
        	
	static byte[] buffL = new byte[1], buffH = new byte[1], ID = new byte[1];
	static byte[] buffer2 = new byte[2];
	private static int offsetX, offsetY, offsetZ;
	private static int[] accelVal = new int[3];
	static Thread pingerThread;
	
	static public void initAccel(){
		accel = new I2C(I2C.Port.kOnboard, 0x1D);
		// Accelerometer
		byte[] reg12 = new byte[1];
		accel.read(0x12,1,reg12);

		//accel.write(CTRL_REG, 0x00);
		accel.write(CTRL_REG+1, 0x57);     //0x20
		//accel.write(CTRL_REG+2, 0x00);   //0x21
		accel.write(CTRL_REG+3, 0x0);//4);  //0x22
		accel.write(CTRL_REG+4, 0x0);//4);   //0x23
		//accel.write(CTRL_REG+5, 0x14);    //0x24
		accel.write(CTRL_REG+6, 0x00);    //0x25
		accel.write(CTRL_REG+7, 0x00);    //0x26
		accel.read(WHO_AM_I, 1, ID);
	}
	
	static public void pingAccel(){
		
		accel.read(OUT_REG,1,buffL);
		accel.read(OUT_REG+1,1,buffH);
		accelVal[0] = concatCorrect(buffH[0], buffL[0]);
		
		accel.read(OUT_REG+2,1,buffL);
		accel.read(OUT_REG+3,1,buffH);
		accelVal[1] = concatCorrect(buffH[0], buffL[0]);
		
		accel.read(OUT_REG+4,1,buffL);
		accel.read(OUT_REG+5,1,buffH);
		accelVal[2] = concatCorrect(buffH[0], buffL[0]);
		
		
		//accelZ = normalize(buffL[0],buffH[0]);
		/*
		ByteBuffer rawData = ByteBuffer.wrap(bufferData);
		rawData.order(ByteOrder.BIG_ENDIAN);
		
		accelX = (int) rawData.getShort();
		accelY = (int) rawData.getShort();
		accelZ = (int) rawData.getShort();
		*/
		 
		
	}

	static public int concatCorrect(byte h, byte l){
		int high = Byte.toUnsignedInt(h);
		int low = Byte.toUnsignedInt(l);
		int test = ((0xFF & high) << 8) + (0xFF & low);
        //String concat = Integer.toHexString(high) + Integer.toHexString(low);
        //int preCheck = Integer.valueOf(concat,16);
        //return (preCheck > 32767) ? preCheck - 65536: preCheck;
        return (test > 32767) ? test - 65536: test;
	}

	static public int normalize(byte h, byte l){
		
		int high = Byte.toUnsignedInt(h);
		int low = Byte.toUnsignedInt(l);
		
		int val = (high << 8) | low;
		
		return (val > 32767) ? val - 65536: val;
	}
	
	static public void pingerStart(){
		Runnable pinger = ()  -> {
			while(pingFlag){
				pingAccel();
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		pingerThread = new Thread(pinger);
		pingFlag = true;
		pingerThread.start();
		
	}
	public static int[] getAccel(){
		return accelVal;
	}
	
	static public void pingerStop(){
		pingFlag = false;
	}
	
}
