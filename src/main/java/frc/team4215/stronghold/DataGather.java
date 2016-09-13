package frc.team4215.stronghold;

import jaci.openrio.module.blackbox.BlackBox;
import jaci.openrio.module.blackbox.BlackBoxContext;

/**
 * Made to gather data
 * 
 * @author Waweru
 */
public class DataGather {

    BlackBoxContext context;
    DriveTrain chassis;
    UI ds;
    
    public DataGather(DriveTrain chassis, Arm arm, UI ds) {
        this.chassis = chassis;
        this.ds = ds;
        
        context = BlackBox.context("gathered_data.csv");
        
        // Logging the volts to the motors
        context.add("Volts: Right ", this::leftVolts);
        context.add("Volts: Left", this::rightVolts);
        
        // Logging the inputs to the drive station
        context.add("Drive: Left", this::leftInputs);
        context.add("Drive: Right", this::rightInputs);
        
        context.add("Arm inputs", arm::getInput);
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
    
    public double zAccel() {
        double[] accel = I2CAccel.getAccel();
        return accel[2];
    }
    
    public double xAccel() {
        double[] accel = I2CAccel.getAccel();
        return accel[0];
    }
    
    public double yAccel() {
        double[] accel = I2CAccel.getAccel();
        return accel[1];
    }
    
    public double xVeloc(){
    	double[] vel = I2CAccel.getVeloc();
    	return vel[0];
    }
    
    public double yVeloc(){
    	double[] vel = I2CAccel.getVeloc();
    	return vel[0];
    }
    
    public double xAngularSpeed() {
        double[] speed = I2CGyro.getAngSpeed();
        //return speed[1];
        return 0;
    }
    
    public double zVeloc(){
    	double[] vel = I2CAccel.getVeloc();
    	return vel[2];
    }
    
    public double xAngle() {
        double[] angles = I2CGyro.getAngles();
        //return angles[0];
        return 0;
    }
    
    public double yAngularSpeed() {
        double[] speed = I2CGyro.getAngSpeed();
        return speed[1];
    }
    
    public double yAngle() {
        double[] angles = I2CGyro.getAngles();
        return angles[1];
    }
    
    public double zAngularSpeed() {
        double[] speed = I2CGyro.getAngSpeed();
        return speed[2];
    }
    
    public double zAngle() {
        double[] angles = I2CGyro.getAngles();
        return angles[2];
    }
}
