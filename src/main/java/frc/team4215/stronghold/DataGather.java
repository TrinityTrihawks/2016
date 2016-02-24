package frc.team4215.stronghold;
import jaci.openrio.module.blackbox.BlackBox;
import jaci.openrio.module.blackbox.BlackBoxContext;

/**
 * Made to gather data
 * @author Waweru
 */
public class DataGather {
	BlackBoxContext context;
	DriveTrain chassis;
	public DataGather( DriveTrain chassis) {
		this.chassis = chassis;
		context = BlackBox.context("gathered_data.txt");
		context.add("Volts: ", this::leftVolts);
		context.add("Volts: ", this::rightVolts);
		context.add("Accel z data", this::zAccel);
		context.add("Accel x data", this::xAccel);
		context.add("Accel Y data", this::yAccel);
	}
	
	public void tick(){
		context.tick();
	}
	
	public double leftVolts(){
		double[] volts = chassis.getVoltages();
		return volts[0];
	}
	
	public double rightVolts(){
		double[] volts = chassis.getVoltages();
		return volts[4];
	}
	public double zAccel(){
		double[] accel = I2CAccel.getAccel();
		return accel[2];
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
