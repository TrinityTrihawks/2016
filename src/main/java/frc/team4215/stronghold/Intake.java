
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

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
    private Joystick GameCube;

    /**
     * The Motor, Victor, for controling the intake.
     */
    private Victor Intake;

    /**
     * Default constructor.
     */
    public Intake() {
        this.GameCube = new Joystick(Const.JoyStick.Num.GameCube);
        this.Intake = new Victor(Const.Motor.Num.Intake);
    }

    /**
     * Run on this
     */
    public void Run() {

        if (this.GameCube.getRawButton(Const.JoyStick.Button.GameCube_A))
            this.Intake.set(Const.Motor.Run.Forward);
        else if (this.GameCube.getRawButton(Const.JoyStick.Button.GameCube_B))
            this.Intake.set(Const.Motor.Run.Backward);
        else
            this.Intake.set(Const.Motor.Run.Stop);
    }
}
