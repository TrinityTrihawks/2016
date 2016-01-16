package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.log.Logger;
import 
import jaci.openrio.toast.lib.module.IterativeModule;

public class RobotModule extends IterativeModule {

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
    Ultrasonic ultra = new Ultrasonic(1,1);
    
    public double getRange(){
		double range = ultra.getRangeInches();
		return range;}
   
    @Override
    
    public void robotInit() {
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);
        double range = getRange();
        System.out.println("Range is " + range);
       
    }
}
