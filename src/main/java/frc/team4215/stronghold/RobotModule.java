package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {
	
	Talon left;
    Talon right;
    Talon left2;
    Talon right2;
    
    Ultrasonic ultra = new Ultrasonic(1,1);
    
    public double getRange(){
		double range = ultra.getRangeInches();
		return range;
		}
    
    double range = getRange();
    
    String range_info = String.valueOf(range);
    
    DriveTrain chassis;
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
        logger.info(range_info);
        left = Registrar.talon(0);
        right = Registrar.talon(1);
        left2 = Registrar.talon(2);
        right2 = Registrar.talon(3);
        
        chassis = new DriveTrain(left,right,left2,right2);//TODO: Module Init
        
    }
    
    @Override
    public void teleopInit(){
    	
    }
    
}
