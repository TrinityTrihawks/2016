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
	UI ds;
	
	public DataGather( DriveTrain chassis, UI ds) {
		this.chassis = chassis;
		this.ds = ds;
		
		context = BlackBox.context("gathered_data.csv");
		
		context.add("Volts: ", this::leftVolts);
		context.add("Volts: ", this::rightVolts);
		
		context.add("Drive Left", this::leftInputs);
		context.add("Drive Right", this::rightInputs);
		
		context.add("Accel Z data", this::zAccel);
		context.add("Accel X data", this::xAccel);
		context.add("Accel Y data", this::yAccel);
		
		
	}
	
	public void tick(){
		context.tick();
	}
	
	
	public double leftInputs(){
		double[] inputs = ds.getDriveInputs();
		return inputs[0];
	}
	
	public double rightInputs(){
		double[] inputs = ds.getDriveInputs();
		return inputs[1];
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
