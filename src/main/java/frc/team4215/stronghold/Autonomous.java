package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Timer;
import jaci.openrio.toast.lib.log.Logger;

/**
 * The class for Autonomous.
 *
 * @author James
 */
public class Autonomous {

    private static Thread threadPing;
    
    // private Victor armMotor, intake;
    
    private DriveTrain dT;
    
    private Arm arm;

    double input;
    
    public Autonomous(DriveTrain dT, Arm arm) {
        this.dT = dT;
        this.arm = arm;
    }
    
    // 75 inches no more no less!!!!!
    public void timeBasedLowBarAuto() {
    	RobotModule.logger.info("Autonomous Starting!!");

        autoArmCycle(-.75,1);
        arm.set(0);
        autoDriveCycle(.5,.525,5);
        Timer.delay(.5);
        autoDriveCycle(-.5,-.525,4);
        dT.drive(0);
        
        RobotModule.logger.info("Autonomous ending!!");
        return;
        
    }
    
    public void moatChevalAuto() {
    	arm.setSafetyEnabled(false);
    	dT.setSafetyEnabled(false);
    	
    	arm.set(1);
    	autoDriveCycle(.5,.525,5);
        Timer.delay(.5);
        autoDriveCycle(-.5,-.525,4);
        dT.drive(0);
        arm.set(0);
        
        arm.setSafetyEnabled(false);
    	dT.setSafetyEnabled(false);
    }
    
    private void autoArmCycle(double volt, double time){
    	arm.setSafetyEnabled(false);
    	arm.set(volt);
    	Timer.delay(time);
    	arm.setSafetyEnabled(true);
    	
    }
    
    private void autoDriveCycle(double voltLeft, double voltRight, double time){
     	dT.setSafetyEnabled(false);
    	dT.drive(voltLeft,voltRight);
    	Timer.delay(time);
    	dT.setSafetyEnabled(true);
    }

}
