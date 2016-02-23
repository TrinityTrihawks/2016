package frc.team4215.stronghold;
import jaci.openrio.module.blackbox.BlackBox;
import jaci.openrio.module.blackbox.BlackBoxContext;
/**
 * Made to gather data
 * @author Waweru
 */
public class DataGather {
	BlackBoxContext context;
	
	public DataGather(Autonomous auto) {
		context = BlackBox.context("gathered_data.txt");
		context.add("Gyro Data",this::zRot);
		context.add("Accel x data", this::xAccel);
		context.add("Accel Y data", this::yAccel);
		context.add("Distance traveled", auto::distanceTraveled);
		context.add("Distance Traveled Pid", auto::getOutPut);
	}
	
	public void tick(){
		context.tick();
	}
	
	public double zRot(){
		double[] angles = I2CGyro.getAngles();
		return angles[2];
	}
	
	public double xAccel(){
		double[] accel = I2CAccel.getAccel();
		return accel[0];
	}
	
	public double yAccel(){
		double[] accel = I2CAccel.getAccel();
		return accel[1];
	}
}
