package frc.team4215.stronghold;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.internal.HardwareTimer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class I2CGyro {
   
    static I2C gyro;
    
    static HardwareTimer hardTimer = new HardwareTimer();
    static Timer.Interface timer;
    
    final static byte WHO_AM_I = 0x0F,
    				  CTRL_REG = 0x20,
    				  OUT_REG = 0x28;
    
    private static double cX = 0, cY = 0, cZ = 0;
    
    static byte[] ID = new byte[1],
    			  dataBuffer = new byte[6];
    
    static private Thread threadPing;
    
    
    public static void initGyro() {
    	
       gyro = new I2C(I2C.Port.kOnboard, 0x6B);
       gyro.read(WHO_AM_I, 1, ID);
       
       if(ID[0] == 212){
    	   RobotModule.logger.info("Gyro active");
       }
       else{
    	   RobotModule.logger.error("Gyro not operating, please check wiring");   
       }
       
       /*
        * So, without explanation the following code is going to
        * seem a bit cryptic. Essentially the Gyro is set up with
        * a series of bits(1's or 0's) and these base 16 numbers correspond 
        * to the particular series of bits you need. the numbers and such are found at
        * *so and so*
        */
       
       gyro.write(CTRL_REG,   0x0F); 
       gyro.write(CTRL_REG+1, 0x00);
       gyro.write(CTRL_REG+2, 0x88);
       gyro.write(CTRL_REG+3, 0x88);
       gyro.write(CTRL_REG+4, 0x00);
       gyro.write(CTRL_REG+5, 0x00);
       
       timer = hardTimer.newTimer();
       timer.start();
    }
    
     public static void pingGyro(){
    	double time = timer.get();
    	gyro.read(OUT_REG, dataBuffer.length, dataBuffer);
    	cX = ((dataBuffer[1] << 8 | 0xFF & dataBuffer[0])*time) % 360;
    	cY = ((dataBuffer[3] << 8 | 0xFF & dataBuffer[2])*time) % 360;
    	cZ = ((dataBuffer[5] << 8 | 0xFF & dataBuffer[4])*time) % 360;
    }
    
     public static void pingerStart(){
    	Runnable pinger = () -> {
    		while(true)
    			pingGyro();
    	};
    	
    	threadPing = new Thread(pinger);
    	threadPing.start();
    }
     
    public static double[] getAngles(){
    	return new double[] {cX, cY, cZ};
    }
     
}