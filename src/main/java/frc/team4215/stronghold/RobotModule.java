package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    Talon left;
    Talon right;
    Talon left2;
    Talon right2;

    Ultrasonic ultra = new Ultrasonic(1, 1);

    DriveTrain chassis;

    Joystick leftStick;
    Joystick rightStick;

    UI driveStation;

    UltraSonic ult;

    public static Logger logger;
    private static final String moduleName = "stronghold";
    private static final String moduleVersion = "0.0.1";

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public String getModuleVersion() {
        return moduleVersion;
    }

    @Override
    public void robotInit() {
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);

        this.left = Registrar.talon(0);
        this.right = Registrar.talon(1);
        this.left2 = Registrar.talon(2);
        this.right2 = Registrar.talon(3);

        this.chassis = new DriveTrain(this.left, this.right, this.left2, this.right2);
        // TODO: Module Init

        this.chassis = new DriveTrain(this.left, this.right, this.left2, this.right2);

        this.leftStick = new Joystick(1);
        this.rightStick = new Joystick(0);

        this.driveStation = new UI(this.leftStick, this.rightStick);

        this.ult = new UltraSonic(1);
        logger.info("Sensing ");
    }

    @Override
    public void teleopPeriodic() {
        double[] inputs = this.driveStation.getInputs();
        this.chassis.drive(inputs[0], inputs[1]);
        logger.info("Sensing " + this.ult.getRangeInch());
    }
}
