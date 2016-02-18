package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
/** @ Author Joshua CC ripping other people's code. Code is mixed in from 2015 robot,
 * but it still should work.
 */
public class DriveTrainControl {

	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Joystick thirdStick = new Joystick(2);
	
	// Talon def
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(2);			
	Talon frontRight = new Talon(3);
	
public void drivingMethod(){
    	
    	double tank = leftStick.getY();
    	double tank2 = rightStick.getY();
    ;
    	
    	frontLeft.set(-tank);
    	backLeft.set(-tank);
    	backRight.set(tank2);
    	frontRight.set(tank2);
    	    }
}
