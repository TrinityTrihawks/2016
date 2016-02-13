
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

        left = Registrar.victor(3);
        left2 = Registrar.victor(1);
        right = Registrar.victor(2);
        right2 = Registrar.victor(0);
        
        chassis = new DriveTrain(left, left2,
                right, right2);

        leftStick = new Joystick(1);
        rightStick = new Joystick(0);
        thirdStick = new Joystick(2);
        
        driveStation = new UI(rightStick);
        ult = new UltraSonic(3);
        arm = new Arm();
    }
    
    @Override
    public void teleopInit() {

    }
    
    @Override
    public void teleopPeriodic() {
        double[] inputs = driveStation.getInputs();
        chassis.drive(inputs[0], inputs[1]);
        arm.Run();

    }
    
    @Override
    public void autonomousPeriodic() {

    }
    
    @Override
    public void autonomousInit() {
        Autonomous.startTimer();

    }
}
