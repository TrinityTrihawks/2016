
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Victor;

/**
 * This is used to control the drive-train
 *
 * @author Waweru
 */
public class DriveTrain implements PIDOutput {
	
	// Used to say what mode it's in
    public enum PIDOutputType{
    	TURN, // Makes the robot turn on the spot
    	DRIVE_STRAIGHT // Drive the robot foward while turning
    }
    
    private Victor leftMotor;
    private Victor rightMotor;
    private Victor rightMotor2;
    private Victor leftMotor2;
    private boolean state;
    private double coeff = .65;
    private double autoDriveSpeed = .25;
    private PIDOutputType PIDState = PIDOutputType.TURN;
    
    
    DriveTrain(Victor leftMotor_, Victor leftMotor_2,
            Victor rightMotor_, Victor rightMotor_2) {
        leftMotor = leftMotor_;
        rightMotor = rightMotor_;
        leftMotor2 = leftMotor_2;
        rightMotor2 = rightMotor_2;
    }
    
    /**
     * Set Drive train speed Inputs from -1 to 1
     *
     * @param leftSpeed
     * @param rightSpeed
     */
    public void drive(double leftSpeed, double rightSpeed) {
        /*
         * The scaling part is moved into a new function to simplify
         * the code. - James
         */
        leftSpeed = coeff*scaling(leftSpeed);
        rightSpeed = coeff*scaling(rightSpeed);
        
        leftMotor.set(leftSpeed);
        leftMotor2.set(leftSpeed);
        rightMotor.set(-rightSpeed);
        rightMotor2.set(-rightSpeed);
    }
    
    /**
     * Sets all motors independently
     * 
     * @param leftSpeed1
     * @param leftSpeed2
     * @param rightSpeed1
     * @param rightSpeed2
     */
    public void setIndependently(double leftSpeed1, double leftSpeed2, 
    							 double rightSpeed1, double rightSpeed2) {
        
        leftMotor.set(-leftSpeed1);
        leftMotor2.set(-leftSpeed2);
        rightMotor.set(rightSpeed1);
        rightMotor2.set(rightSpeed2);
    }
    
    /**
     * Chooses coeffecients for drivetrain
     * @param newState
     */
    public void setCoeffState(boolean newState){
    	state = newState;
    	if(state){
    		coeff = 1;
    	}
    	else{
    		coeff = .65;
    	}
    }
    
    /**
     * Scaling because Victor does not respond to voltage less than 4%
     * in either direction.
     *
     * @param speed
     * @return scaled speed
     */
    private static double scaling(double speed) {
        if (speed == 0) return 0d;
        else return Math.signum(speed)
                * ((Math.abs(speed) * .96) + .04);
    }
    
    public double[] getVoltages() {
    	return new double[] { leftMotor.get(),leftMotor2.get(),
    						rightMotor.get(),rightMotor2.get()};
    }
    
    /**
     * You can use this function when the left speed and right speed
     * is the same.
     *
     * @author James
     * @param speed
     */
    public void drive(double speed) {
        drive(speed, speed);
    }
    
    /**
     *Implementation of PIDOutput depends on PIDState
     *@author waweros
     *@param speed
     */
    public void pidWrite(double speed){
    	switch(PIDState){
    		case TURN:
    			turnOutput(speed);
    			break;
    		case DRIVE_STRAIGHT:
    			driveOutput(speed);
    			break;
    	}
    }
    
    /**
     * Turns Robot to the right at given speed
     * @author waweros
     * @param speed
     */
    public void turnOutput(double speed){
    	drive(speed,-speed);
    }
    
    /**
     * makes the robot deviate from a straight path
     * @author waweros
     * @param speed
     */
    public void driveOutput(double speed){
    	drive(autoDriveSpeed + speed, autoDriveSpeed - speed);
    }
    
    /**
     * Gives the current PIDState for the DriveTrain
     * @return
     */
    public PIDOutputType getPIDState(){
    	return PIDState;
    }
    
    /**
     * Sets the PIDState 
     * @param state
     */
    public void setPIDState(PIDOutputType state){
    	PIDState = state;
    }
    
    /**
     * Sets motors safetyEnabled key
     * @param enabled
     */
    public void setSafetyEnabled(boolean enabled){
    	leftMotor.setSafetyEnabled(enabled);
    	leftMotor2.setSafetyEnabled(enabled);
    	rightMotor.setSafetyEnabled(enabled);
    	rightMotor2.setSafetyEnabled(enabled);
    	
    	return;
    }
}
