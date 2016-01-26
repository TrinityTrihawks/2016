
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    private Talon left, right, left2, right2;

    private Ultrasonic ultra = new Ultrasonic(1, 1);

    public double getRange() {

        double range = this.ultra.getRangeInches();
        return range;
    }

    double range = this.getRange();

    private String range_info = String.valueOf(this.range);

    private DriveTrain chassis;

    private Joystick leftStick, rightStick;

    private UI driveStation;

    public static Logger logger;

    private static final String ModuleName = "stronghold";

    private static final String ModuleVersion = "0.0.1";

    @Override
    public String getModuleName() {

        return ModuleName;
    }

    @Override
    public String getModuleVersion() {

        return ModuleVersion;
    }

    @Override
    public void robotInit() {

        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);

        logger.info(this.range_info);

        this.left = Registrar.talon(0);
        this.right = Registrar.talon(1);
        this.left2 = Registrar.talon(2);
        this.right2 = Registrar.talon(3);

        this.chassis = new DriveTrain(this.left, this.right, this.left2,
                this.right2);// TODO: Module Init

        this.chassis = new DriveTrain(this.left, this.right, this.left2,
                this.right2);

        this.leftStick = new Joystick(1);
        this.rightStick = new Joystick(0);

        this.driveStation = new UI(this.leftStick, this.rightStick);
    }

    @Override
    public void teleopPeriodic() {

        double[] inputs = this.driveStation.getInputs();
        this.chassis.drive(inputs[0], inputs[1]);
    }

}
