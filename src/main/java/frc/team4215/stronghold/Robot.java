
package frc.team4215.stronghold;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
    
    // RobotDrive Drive;
    // Joystick stick;
    private I2C I2CBus;

    private short cX, cY, cZ;

    private byte[] dataBuffer;

    private ByteBuffer compBuffer;

    // FieldCentricController FCC();

    public Robot() {
        this.cX = 0;
        this.cY = 0;
        this.cZ = 0;
        this.dataBuffer = new byte[6];
        this.compBuffer = ByteBuffer.wrap(this.dataBuffer);
        // You wanna have all the initialization together in the
        // constructor.
        // - James
        this.I2CBus = new I2C(I2C.Port.kOnboard, 0x1E);
        // Drive = new RobotDrive(0, 1);
        // Drive.setExpiration(0.1);
        // stick = new Joystick(0);

    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    @Override
    public void autonomous() {

    }

    /**
     * Runs the motors with arcade steering.
     */
    @Override
    public void operatorControl() {
        // Drive.setSafetyEnabled(true);

        this.I2CBus.write(0x02, 0x00);
        //// If you need a constant for these two numbers let me know.
        //// - James

        while (this.isOperatorControl() && this.isEnabled()) {

            this.I2CBus.read(0x03, 6, this.dataBuffer);
            //// The same here
            this.compBuffer.order(ByteOrder.BIG_ENDIAN);

            this.cX = this.compBuffer.getShort();
            this.cY = this.compBuffer.getShort();
            this.cZ = this.compBuffer.getShort();

            SmartDashboard.putNumber("CompX", this.cX);
            SmartDashboard.putNumber("CompY", this.cY);
            SmartDashboard.putNumber("CompZ", this.cZ);

            // Drive.mecanumDrive_Cartesian(stick.getX(),
            // stick.getY(),
            // stick.getTwist(), 0.0);

            Timer.delay(0.065); // wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    @Override
    public void test() {

    }
}
