package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import jaci.openrio.module.blackbox.BlackBox;
import jaci.openrio.module.blackbox.BlackBoxContext;

/**
 * Made to gather data
 * 
 * @author Waweru
 */
public class DataGather {
	Accelerometer accel;
    BlackBoxContext context;
    DriveTrain chassis;
    UI ds;
    
    public DataGather(DriveTrain chassis, Arm arm, UI ds) {
        this.chassis = chassis;
        this.ds = ds;
        
        context = BlackBox.context("gathered_data.csv");
        accel = new BuiltInAccelerometer();
        // Logging the volts to the motors
        context.add("Volts: Right ", this::leftVolts);
        context.add("Volts: Left", this::rightVolts);
        
        
        // Logging the inputs to the drive station
        context.add("Drive: Left", this::leftInputs);
        context.add("Drive: Right", this::rightInputs);
        
        context.add("Arm inputs", arm::getInput);
        
        //Logging acceleration
        context.add("X acceleration", accel::getX);
        context.add("Y acceleration", accel::getY);
        context.add("Z acceleration", accel::getZ);
    }
    
    public void tick() {
        context.tick();
    }
    
    public double leftInputs() {
        double[] inputs = ds.getDriveInputs();
        return inputs[0];
    }
    
    public double rightInputs() {
        double[] inputs = ds.getDriveInputs();
        return inputs[1];
    }
    
    public double leftVolts() {
        double[] volts = chassis.getVoltages();
        return volts[0];
    }
    
    public double rightVolts() {
        double[] volts = chassis.getVoltages();
        return volts[3];
    }
    
}
