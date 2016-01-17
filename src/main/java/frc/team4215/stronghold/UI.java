package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;

public class UI {
	Joystick leftStick;
	Joystick rightStick;

	UI(Joystick leftStick_,Joystick rightStick_){
		leftStick = leftStick_;
		rightStick = rightStick_;
	}
	
	double[] getInputs(){
		double[] inputs = {
		leftStick.getRawAxis(0),
		rightStick.getRawAxis(1)
		};
		return inputs;
	}
	
}
