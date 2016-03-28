
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
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
 * <dd>{@link Arm#Run()}</dd>
 * </dl>
 *
 * @author James Yu
 */
public class Arm {

    /**
     * The Joystick used to control arms and the intake.
     */
    private Joystick gameCube;
    
    /**
     * The Motor, Victor, for controling the arm.
     */
    private Victor armMotor1;
    private Victor armMotor2;
    private boolean state;
    // The arm is really sensitive!!!!!
    private static double axisCoeff = .35;
    
    /**
     * Default constructor. -- Waweru Turns out the arm has 2 motors
     */
    public Arm() {
        gameCube = new Joystick(Const.JoyStick.Num.GameCube);
        armMotor1 = Registrar.victor(Const.Motor.Num.Arm1);
        armMotor2 = Registrar.victor(Const.Motor.Num.Arm2);
        state = false;
    }

    /**
     * Run on this
     */
    public void Run() {
        
        armMotor1.set(axisCoeff * gameCube
                .getRawAxis(Const.JoyStick.Axis.GameCubeCtrl_UD));
        armMotor2.set(axisCoeff * gameCube
                .getRawAxis(Const.JoyStick.Axis.GameCubeCtrl_UD));
        return;
    }
    
    public double getInput(){
    	return armMotor1.get();
    }
    
    public void set(double setValue) {
        armMotor1.set(axisCoeff*setValue);
        armMotor2.set(axisCoeff*setValue);
        
        return;
    }
    
    public void setSafetyEnabled(boolean enabled){
    	armMotor1.setSafetyEnabled(enabled);
    	armMotor2.setSafetyEnabled(enabled);
    	
    	return;
    }
    
    public void changeState(boolean newState){
    	state = newState;
    	if(state){
    		axisCoeff = .5;
    	}
    	else{
    		axisCoeff = .35;
    	}
    }
}
