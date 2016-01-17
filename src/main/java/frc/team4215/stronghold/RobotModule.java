package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {
	
	Talon left;
    Talon right;
    Talon left2;
    Talon right2;
    
    DriveTrain chassis;
    
    Joystick  leftStick;
    Joystick rightStick;
    
    UI driveStation;
    
    public static Logger logger;
    private static String ModuleName = 
        "stronghold";
    private static String ModuleVersion = 
        "0.0.1";

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
        
        left = Registrar.talon(0);
        left2 = Registrar.talon(1);
        right = Registrar.talon(2);
        right2 = Registrar.talon(3);
        
        chassis = new DriveTrain(left,left2, right,right2);
        
        leftStick = new Joystick(1);
        rightStick = new Joystick(0);
        
        driveStation = new UI(leftStick,rightStick);
    }
    
    @Override
    public void teleopPeriodic(){
    	double[] inputs = driveStation.getInputs();
    	chassis.drive(inputs[0], inputs[1]);
    }
    
    @Override
    public void autonomousPeriodic(){
    	
    }
}
