
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
    private Victor armMotor;

    /**
     * Default constructor.
     */
    public Arm() {
        this.gameCube = new Joystick(Const.JoyStick.Num.GameCube);
        this.armMotor = Registrar.victor(Const.Motor.Num.Arm);
    }
    
    /**
     * Run on this
     */
    public void Run() {

        this.armMotor.set(this.gameCube
                .getRawAxis(Const.JoyStick.Axis.GameCubeCtrl_UD));
        return;
    }
    
    public void set(double setValue) {
        this.armMotor.set(setValue);
    }
}
