
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    private Victor left, right, left2, right2;
    
    private DriveTrain chassis;
    private Arm arm;
    
    private Joystick leftStick, rightStick, thirdStick;

    private UI driveStation;
    
    UltraSonic ult;
    
    public static Logger logger;
    
    private static final String ModuleName = "stronghold";
    
    private static final String ModuleVersion = "0.0.1";
    
    @Override
    public String getModuleName() {
        
        return RobotModule.ModuleName;
    }
    
    @Override
    public String getModuleVersion() {
        
        return RobotModule.ModuleVersion;
    }
    
    @Override
    public void robotInit() {
        
        RobotModule.logger =
                new Logger("stronghold", Logger.ATTR_DEFAULT);

        this.left = Registrar.victor(3);
        this.left2 = Registrar.victor(1);
        this.right = Registrar.victor(2);
        this.right2 = Registrar.victor(0);
        
        this.chassis = new DriveTrain(this.left, this.left2,
                this.right, this.right2);

        this.leftStick = new Joystick(1);
        this.rightStick = new Joystick(0);
        this.thirdStick = new Joystick(2);
        
        this.driveStation = new UI(this.rightStick);
        this.ult = new UltraSonic(3);
        arm = new Arm();
    }
    
    @Override
    public void teleopInit() {

    }
    
    @Override
    public void teleopPeriodic() {
        double[] inputs = this.driveStation.getInputs();
        this.chassis.drive(inputs[0], inputs[1]);
        arm.Run();

    }
    
    @Override
    public void autonomousPeriodic() {

    }
    
    @Override
    public void autonomousInit() {
        //Autonomous.startTimer();

    }
}
