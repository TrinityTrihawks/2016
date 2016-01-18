package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.module.IterativeModule;

/**
 * Prototype that measures distance as an analog voltage with UltraSonic sensor, then converts voltage into understandable units.
 * @author Jack Rausch
 *
 */
public class UltraProto extends IterativeModule {
	
	Ultrasonic ultra = new Ultrasonic(1,1);
	// creates an object and assigns variable 'ultra' to be the UltraSonic sensor which uses Output1 for echo-pulse and Input1 for trigger-pulse
	AnalogInput anInput = new AnalogInput(1);
	AnalogOutput anOutput = new AnalogOutput(1);
	public void robotInit(){
		ultra.setAutomaticMode(true);
	//enables Automatic mode
	}
	
	
	double voltRange = 5.0;
	double distanceRange;
	double range;
	double avrange;
	//defines variables for values to be inputed later
	
	public double getRange(){
		double range = anInput.getVoltage();
		//takes the analog voltage as a double
		return ((range / voltRange)*distanceRange);
		// converts voltage range into a percentage, then multiplies it by the overall distance to make it more understandable
	}
	
	public double getAverageRange(){
		double avrange = anInput.getAverageVoltage();
		//takes the average analog voltage as a double
		return ((avrange / voltRange)*distanceRange);
		// converts average voltage range into a percentage, then multiplies it by the maximum distance to make it into understandable units
	}
	
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModuleVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
