
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.registry.Registrar;

/**
 * <dl>
 * <dt>Properties:</dt>
 * <dd><strong>Private:</strong></dd>
 * <dd>{@link Joystick} <i>GameCube</i></dd>
 * <dd>{@link Victor} <i>ArmMotor</i></dd>
 * <dt>Methods:</dt>
 * <dd><strong>Constructors:</strong></dd>
 * <dd>{@link Arm#Arm()}</dd>
 * <dd><strong>Other Methods:</strong></dd>
 * <dd>{@link Arm#inOrOut()}</dd>
 * </dl>
 *
 * @author James Yu
 */
public class Arm implements PIDOutput {
    
    /**
     * The Motor, Victor, for controling the arm.
     */
    private Victor armMotor1;
    private Victor armMotor2;
    private boolean state;
    // The arm is really sensitive!!!!!
    private static double axisCoeff = .35;
    
    /**
     * Default constructor.
     */
    public Arm() {
        armMotor1 = Registrar.victor(Const.Motor.Num.Arm1);
        armMotor2 = Registrar.victor(Const.Motor.Num.Arm2);
        state = false;
    }
    
    /**
     * Gets the last input to the arm
     * @return
     */
    public double getInput(){
    	return armMotor1.get();
    }
    
    /**
     * Sets the motors to the setValue 
     * multiplied by a coeffecient
     * @param setValue
     */
    public void set(double setValue) {
        armMotor1.set(axisCoeff*setValue);
        armMotor2.set(axisCoeff*setValue);
        
        return;
    }
    
    
    public void pidWrite(double setValue){
    	armMotor1.set(setValue);
    	armMotor2.set(setValue);
    	
    	return;
    }
    
    /**
     * sets safety for component motors
     * @param enabled
     */
    public void setSafetyEnabled(boolean enabled){
    	armMotor1.setSafetyEnabled(enabled);
    	armMotor2.setSafetyEnabled(enabled);
    	
    	return;
    }
    
    /**
     * Allows the set coeffecient to swirch between . and .35
     * @param newState
     */
    public void setState(boolean newState){
    	state = newState;
    	if(state){
    		axisCoeff = .5;
    	}
    	else{
    		axisCoeff = .35;
    	}
    }
}
