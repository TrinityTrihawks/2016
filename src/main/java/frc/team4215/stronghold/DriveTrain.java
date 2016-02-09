
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Talon;

/**
 * This is used to control the drive-train
 *
 * @author waweros
 */
public class DriveTrain {
    
    private Talon leftMotor, rightMotor, rightMotor2, leftMotor2;
    // You might wanna know if leftMotor is the front or the back one.
    // - James
    
    DriveTrain(Talon leftMotor_, Talon leftMotor_2, Talon rightMotor_,
            Talon rightMotor_2) {
        this.leftMotor = leftMotor_;
        this.rightMotor = rightMotor_;
        this.leftMotor2 = leftMotor_2;
        this.rightMotor2 = rightMotor_2;
    }
    
    /**
     * Set Drive train speed Inputs from -1 to 1
     *
     * @param leftSpeed
     * @param rightSpeed
     */
    public void drive(double leftSpeed, double rightSpeed) {
        
        this.leftMotor.set(leftSpeed);
        this.leftMotor2.set(leftSpeed);
        this.rightMotor.set(rightSpeed);
        this.rightMotor2.set(rightSpeed);
    }

    public void drive(double speed) {
        this.drive(speed, speed);
    }
}
