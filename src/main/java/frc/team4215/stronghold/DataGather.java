package frc.team4215.stronghold;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import jaci.openrio.module.blackbox.BlackBox;
import jaci.openrio.module.blackbox.BlackBoxContext;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.lib.state.RobotState;

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
    
    public DataGather(DriveTrain chassis, Arm arm, UI ds, AnalogGyro gyro) {
        this.chassis = chassis;
        this.ds = ds;
        
        context = BlackBox.context("gathered_data");
        accel = new BuiltInAccelerometer();
        PowerDistributionPanel pdp = new PowerDistributionPanel();
        
        // Logging field state
        context.add("Disabled",from( () -> {return StateTracker.currentState == RobotState.DISABLED;}));
        context.add("Auto",from( () -> {return StateTracker.currentState == RobotState.AUTONOMOUS;}));
        context.add("Teleop",from(() -> {return StateTracker.currentState == RobotState.TELEOP;}));
        context.add("Test",from(() -> {return StateTracker.currentState == RobotState.TEST;}));
        
        // Logging the volts to the motors
        context.add("Volts: Right ", this::leftVolts);
        context.add("Volts: Left", this::rightVolts);
        
        // Logging power info
        context.add("PDP Voltage:", pdp::getVoltage);
        context.add("VRM Input Voltage", ControllerPower::getInputVoltage);
        context.add("PDP Temp", pdp::getTemperature);
        
        // Logging the inputs to the drive station
        context.add("Drive: Left", this::leftInputs);
        context.add("Drive: Right", this::rightInputs);
        context.add("Arm inputs", arm::getInput);
        
        //Logging acceleration
        context.add("X acceleration", accel::getX);
        context.add("Y acceleration", accel::getY);
        context.add("Z acceleration", accel::getZ);
        context.add("Gyroscope:", gyro::getAngle);
    }
    
    public void tick() {
        context.tick();
    }
    
    public static Supplier<Number> from(Supplier<Boolean>supp){
    	return () -> {return supp.get() ? 1 : 0;};
    }
    
    private double leftInputs() {
        double[] inputs = ds.getDriveInputs();
        return inputs[0];
    }
    
    private double rightInputs() {
        double[] inputs = ds.getDriveInputs();
        return inputs[1];
    }
    
    private double leftVolts() {
        double[] volts = chassis.getVoltages();
        return volts[0];
    }
    
    private double rightVolts() {
        double[] volts = chassis.getVoltages();
        return volts[3];
    }
    
}
