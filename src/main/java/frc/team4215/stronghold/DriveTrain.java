package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;

/**
 * This is used to control the drive-train
 * 
 * @author waweros
 *
 */
public class DriveTrain {
	Victor leftMotor;
	Victor rightMotor;
	Victor rightMotor2;
	Victor leftMotor2;
	
	DriveTrain(Victor leftMotor_, Victor leftMotor_2, 
			Victor rightMotor_, Victor rightMotor_2){
		leftMotor = leftMotor_;
		rightMotor = rightMotor_;
		leftMotor2 = leftMotor_2;
		rightMotor2 = rightMotor_2;
	}

	/**
	 * Set Drive train speed 
	 * Inputs from -1 to  1
	 * 
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void drive(double leftSpeed, double rightSpeed){
		leftMotor.set(leftSpeed);
		leftMotor2.set(leftSpeed);
		rightMotor.set(rightSpeed);
		rightMotor2.set(rightSpeed);
		}
}
