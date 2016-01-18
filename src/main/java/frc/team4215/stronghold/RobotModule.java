package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.log.Logger;
import 
jaci.openrio.toast.lib.module.IterativeModule;

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
    AnalogInput anInput = new AnalogInput(1);
	AnalogOutput anOutput = new AnalogOutput(1);
    double voltRange = 5.0;
	double distanceRange;
	double range;
	double avrange;
    
    public double getRange(){
    	double range = anInput.getVoltage();
		//takes the analog voltage as a double
		return ((range / voltRange)*distanceRange);
		// converts voltage range into ab  percentage, then multiplies it by the overall distance to make it more understandable
    }
    
    public double getAverageRange(){
		double avrange = anInput.getAverageVoltage();
		//takes the average analog voltage as a double
		return ((avrange / voltRange)*distanceRange);
		// converts average voltage range into a percentage, then multiplies it by the maximum distance to make it into understandable units
	}
   
    @Override
    
    public void robotInit() {
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);
        double range = getRange();
        String str_range = String.valueOf(range);
        String str_avrange = String.valueOf(avrange);
        logger.info("range is..." + str_range);
        logger.info("average range is..." + str_avrange);
       
    }
}
