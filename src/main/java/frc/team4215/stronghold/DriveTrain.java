
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;

/**
 * This is used to control the drive-train
 *
 * @author waweros
 */
public class DriveTrain {

    Victor leftMotor;
    Victor rightMotor;
    Victor rightMotor2;
    Victor leftMotor2;

    DriveTrain(Victor leftMotor_, Victor leftMotor_2,
            Victor rightMotor_, Victor rightMotor_2) {
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
        /*
         * The Victors don't respond to a voltage of less then 4%
         * either direction so I provided some scaling.
         */
        /*
         * The scaling part is moved into a new function to simplify
         * the code. - James
         */
        leftSpeed = DriveTrain.scaling(leftSpeed);
        rightSpeed = DriveTrain.scaling(rightSpeed);

        this.leftMotor.set(leftSpeed);
        this.leftMotor2.set(leftSpeed);
        this.rightMotor.set(rightSpeed);
        this.rightMotor2.set(rightSpeed);
    }

    /**
     * Scaling because Victor does not response to volts less than 4%
     * either direction.
     *
     * @param speed
     * @return scaled speed
     */
    private static double scaling(double speed) {
        if (Math.abs(speed) == 0) return 0d;
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
        this.drive(speed, speed);
    }

}
