package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.I2C;

public class I2CAccelerometer {
	static I2C accel;
	
	final static byte WHO_AM_I = 0x0F,
			  CTRL_REG = 0x20,
			  OUT_REG = 0x28;
	
	
}
