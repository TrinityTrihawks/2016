
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.registry.Registrar;

/**
 * <dl>
 * <dt>Properties:</dt>
 * <dd><strong>Private:</strong></dd>
 * <dd>{@link Joystick} <i>GameCube</i></dd>
 * <dd>{@link Victor} <i>Intake</i></dd>
 * <dt>Methods:</dt>
 * <dd><strong>Constructors:</strong></dd>
 * <dd>{@link Intake#Intake()}</dd>
 * <dd><strong>Other Methods:</strong></dd>
 * <dd>{@link Intake#Run()}</dd>
 * </dl>
 *
 * @author James Yu
 */
public class Intake {

    /**
     * The Joystick used to control arms and the intake.
     */
    private Joystick gameCube;
    
    /**
     * The Motor, Victor, for controling the intake.
     */
    private Victor intake;
    static double coeff = .5;
    /**
     * Default constructor.
     */
    public Intake() {
        this.gameCube = new Joystick(Const.JoyStick.Num.GameCube);
        this.intake = Registrar.victor(Const.Motor.Num.Intake);
    }
    
    /**
     * Run on this
     */
    public void Run() {
        
        if (gameCube
                .getRawButton(Const.JoyStick.Button.GameCube_A))
            intake.set(Const.Motor.Run.Forward);
        else if (gameCube
                .getRawButton(Const.JoyStick.Button.GameCube_B))
            intake.set(Const.Motor.Run.Backward);
        else intake.set(Const.Motor.Run.Stop);
    }
    public double get(){
    	return intake.get();
    }
    public void set(double setValue) {
        intake.set(setValue);
    }
}
