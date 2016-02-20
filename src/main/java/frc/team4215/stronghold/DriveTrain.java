
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
    double coeff = .5;
    
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
        
        leftMotor.set(-leftSpeed);
        leftMotor2.set(-leftSpeed);
        rightMotor.set(rightSpeed);
        rightMotor2.set(rightSpeed);
    }
    
    public void driveNonScaled(double leftSpeed, double rightSpeed) {
        /*
         * The Victors don't respond to a voltage of less then 4%
         * either direction so I provided some scaling.
         */
        /*
         * The scaling part is moved into a new function to simplify
         * the code. - James
         */
        leftSpeed = scaling(leftSpeed);
        rightSpeed = scaling(rightSpeed);
        
        leftMotor.set(-leftSpeed);
        leftMotor2.set(-leftSpeed);
        rightMotor.set(rightSpeed);
        rightMotor2.set(rightSpeed);
    }
    
    /**
     * Scaling because Victor does not response to volts less than 4%
     * either direction.
     *
     * @param speed
     * @return scaled speed
     */
    private static double scaling(double speed) {
        if (speed == 0) return 0d;
        else return Math.signum(speed)
                * ((Math.abs(speed) * .96) + .04);
    }
    
    /**
     * You can use this function when left speed and right speed are
     * the same.
     *
     * @author James
     * @param speed
     */
    public void drive(double speed) {
        drive(speed, speed);
    }
    
}
