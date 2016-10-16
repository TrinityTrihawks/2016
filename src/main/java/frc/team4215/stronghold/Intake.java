
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
     * The Motor, Victor, for controlling the intake.
     */
    private Victor intake;
    
    // Constants!!!
    static private final double inCoeff = .75;
    static private final double outCoeff = 1;
    static private final int INTAKE_VICTOR = 5;
    
    public Intake() {
        intake = Registrar.victor(INTAKE_VICTOR);
    }
    
    public void inOrOut(boolean in, boolean out){
    	if(in)
            intake.set(outCoeff);
        else if (out)
            intake.set(-inCoeff);
        else intake.set(0);
    }
    
    public double get(){
    	return intake.get();
    }
    
    public void set(double setValue) {
        intake.set(setValue);
    }
}
