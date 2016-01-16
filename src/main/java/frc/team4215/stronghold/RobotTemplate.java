package frc.team4215.stronghold;



import edu.wpi.first.wpilibj.Ultrasonic;
import jaci.openrio.toast.lib.module.IterativeModule;

public class RobotTemplate extends IterativeModule{
	/**
	 *Implements ultrasonic sensor for a robot 
	 * @return 
	 */

	
	Ultrasonic ultra = new Ultrasonic(1,1);
	
	
	
	
	public void robotInit(){
		ultra.setAutomaticMode(true);
	}
	
	public double getRange(){
		double range = ultra.getRangeInches();
		return range;
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
