package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {
	
	Victor left;
    Victor right;
    Victor left2;
    Victor right2;
    
    
    DriveTrain chassis;
    
    Joystick  leftStick;
    Joystick rightStick;

    Victor leftMotor;
	Victor rightMotor;
	Victor rightMotor2;
	Victor leftMotor2;
	Victor intake;
	Victor arm;
    
    UI driveStation;
    
    UltraSonic ult;

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
        
        left = Registrar.victor(0);
        left2 = Registrar.victor(1);
        right = Registrar.victor(2);
        right2 = Registrar.victor(3);
        
        chassis = new DriveTrain(left,left2, right,right2);
      
        leftStick = new Joystick(1);
        rightStick = new Joystick(0);
        
        driveStation = new UI(leftStick,rightStick,leftStick, leftMotor,rightMotor,leftMotor2,rightMotor2,intake,arm);
        
        ult = new UltraSonic(1);
        logger.info("Sensing ");
        ult = new UltraSonic(1);
    }
    
    @Override
    public void teleopInit(){
    	
    }
    
    @Override
    public void teleopPeriodic(){
    	double[] inputs = driveStation.getInputs();
    	chassis.drive(inputs[0], inputs[1]);
    	logger.info("Sensing " + ult.getRangeInch());
    }
    
    @Override
    public void autonomousPeriodic(){
    	
    }
}