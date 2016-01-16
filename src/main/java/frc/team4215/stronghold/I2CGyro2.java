package frc.team4215.stronghold;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class I2CGyro2 extends SampleRobot {
   // RobotDrive Drive;
   // Joystick stick;
    I2C I2CBus;
    
    short cX = 0, cY = 0, cZ = 0;

    byte[] dataBuffer = new byte[6];
    ByteBuffer compBuffer = ByteBuffer.wrap(dataBuffer);
    
    
 //   FieldCentricController FCC();
    
    
    public I2CGyro2() {
    	
        I2CBus = new I2C(I2C.Port.kOnboard, 0x1E);
       // Drive = new RobotDrive(0, 1);
       // Drive.setExpiration(0.1);
       // stick = new Joystick(0);
       
       
       
   
       
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
       
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
      //  Drive.setSafetyEnabled(true);
    		
    		
    		I2CBus.write(0x02, 0x00);
    	   
    		
    	
    	
        while (isOperatorControl() && isEnabled()) {
        	
        	
    	    
            
        	I2CBus.read(0x03, 6, dataBuffer);
      	
        	compBuffer.order(ByteOrder.BIG_ENDIAN);
        	
        	cX = compBuffer.getShort();
        	cY = compBuffer.getShort();
        	cZ = compBuffer.getShort();
   
        
      
      		
        	
        	SmartDashboard.putNumber("CompX", cX);
        	SmartDashboard.putNumber("CompY", cY);
        	SmartDashboard.putNumber("CompZ", cZ);
        	
       //    Drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getTwist(), 0.0);
           
           
           Timer.delay(0.065);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}