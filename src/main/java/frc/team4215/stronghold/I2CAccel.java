package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class I2CAccel {
	static I2C accel;
	
	
	final static byte WHO_AM_I = 0x0F,
			  CTRL_REG = 0x20,
			  OUT_REG = 0x28;
	static byte[] bufferData = new byte[6];
	static int[] data = new int[6];
	static Thread pingerThread;
	
	static public void initAccel(){
		accel = new I2C(I2C.Port.kOnboard, 0x1D);
		
	}
	
	static public void pingAccel(){
		accel.read(OUT_REG,6,bufferData);
		
		for(int i = 0; i < bufferData.length;i++){
			String tmp = Integer.toBinaryString(bufferData[i]);
			
			if(tmp.length() == 32){
				tmp = tmp.substring(23);
				RobotModule.logger.error("It's  doing it again!!");
			}
			else if(tmp.length() != 32 && tmp.length() > 8){
				RobotModule.logger.error("It's got " + tmp.length() + " bits!!!");
			}
			
			data[i] = Integer.valueOf(tmp, 2);
		}
	}
	
	static public void pingerStart(){
		Runnable pinger = ()  -> {
			while(true)
				pingAccel();
		};
		
		pingerThread = new Thread(pinger);
		pingerThread.start();
		
	}
	
	static public int[] getAccel(){
		return data;
	}
}
