
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

    /**
     * Default constructor.
     * -- Waweru
     * Turns out the arm has 2 motors
     */
    public Arm() {
        this.gameCube = new Joystick(Const.JoyStick.Num.GameCube);
        this.armMotor1 = Registrar.victor(Const.Motor.Num.Arm1);
        this.armMotor2 = Registrar.victor(Const.Motor.Num.Arm2);
    }
    
    /**
     * Run on this
     */
    public void Run() {

        this.armMotor1.set(this.gameCube
                .getRawAxis(Const.JoyStick.Axis.GameCubeCtrl_UD));
        this.armMotor2.set(this.gameCube
                .getRawAxis(Const.JoyStick.Axis.GameCubeCtrl_UD));
        return;
    }
    
    public void set(double setValue) {
        this.armMotor1.set(setValue);
        this.armMotor2.set(setValue);
    }
}
