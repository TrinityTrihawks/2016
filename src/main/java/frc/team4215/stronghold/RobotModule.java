
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

        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
        thirdStick = new Joystick(2);

        driveStation = new UI(rightStick, leftStick);
        
        ult = new UltraSonic(3);

    }
    
    @Override
    public void teleopPeriodic() {
        double[] inputs = driveStation.getDriveInputs();
        chassis.drive(inputs[0], inputs[1]);
    }
    
    @Override
    public void autonomousPeriodic() {

    }
    
    @Override
    public void autonomousInit() {
        Autonomous.startTimer();

    }

    @Override
    public void teleopInit() {
    
    }
}
