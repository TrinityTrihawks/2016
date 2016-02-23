
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;

/**
 * This is used to control the drive-train
 *
 * @author Waweru
 */
public class DriveTrain {
    
    Victor leftMotor;
    Victor rightMotor;
    Victor rightMotor2;
    Victor leftMotor2;
    boolean state;
    double coeff = .65;
    
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
         * The Victors don't respond to a voltage of less then 4%
         * either direction so I provided some scaling.
         */
        /*
         * The scaling part is moved into a new function to simplify
         * the code. - James
         */
        leftSpeed = coeff*scaling(leftSpeed);
        rightSpeed = coeff*scaling(rightSpeed);
        RobotModule.logger.info("Coeff used: " + coeff);
        leftMotor.set(-leftSpeed);
        leftMotor2.set(-leftSpeed);
        rightMotor.set(rightSpeed);
        rightMotor2.set(rightSpeed);
    }
    
    public void setIndependently(double leftSpeed1, double leftSpeed2, 
    							 double rightSpeed1, double rightSpeed2) {
        /*
         * The Victors don't respond to a voltage of less then 4%
         * either direction so I provided some scaling.
         */
        
        leftMotor.set(-leftSpeed1);
        leftMotor2.set(-leftSpeed2);
        rightMotor.set(rightSpeed1);
        rightMotor2.set(rightSpeed2);
    }
    public void setState(boolean newState){
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
    
}
