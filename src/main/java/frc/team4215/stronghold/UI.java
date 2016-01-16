package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;

public class UI {
	Joystick leftStick;
	Joystick rightStick;

	double[] getInputs(){
		double[] inputs = {
		leftStick.getRawAxis(1),
		rightStick.getRawAxis(1)
		};
		return inputs;
	}
	
}
