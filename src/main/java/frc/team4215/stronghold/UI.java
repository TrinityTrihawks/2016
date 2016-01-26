
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;

public class UI {
    
    private Joystick leftStick, rightStick;
    
    public UI(Joystick leftStick_, Joystick rightStick_) {
        this.leftStick = leftStick_;
        this.rightStick = rightStick_;
    }
    
    public double[] getInputs() {
        
        /*
         * double[] inputs = { this.leftStick.getRawAxis(1),
         * this.rightStick.getRawAxis(1) }; return inputs;
         */
        return new double[] { this.leftStick.getRawAxis(1),
                this.rightStick.getRawAxis(1) };
        // You don't need to make variable for it
    }
    
}
