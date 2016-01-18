package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;

public class UI {
	Joystick[] driveSticks = new Joystick[2];
	Joystick thirdstick;

	public UI(Joystick leftStick_,Joystick rightStick_, Joystick thirdstick_){
		driveSticks[0] = leftStick_;
		driveSticks[1] = rightStick_;
		thirdstick = thirdstick_;
	}
	
	public UI(Joystick driveStick_, Joystick thirdstick_){
		driveSticks[0] = driveStick_;
		thirdstick = thirdstick_;
	}
	
	double[] getInputs(){
		double[] inputs = new double[2];
		if(driveSticks.length == 1){
			inputs[0] = driveSticks[0].getRawAxis(1);
			inputs[1] = driveSticks[0].getRawAxis(5);
		}
		else{
			inputs[0] = driveSticks[0].getRawAxis(1);
			inputs[1] = driveSticks[1].getRawAxis(1);
		}
		return inputs;
	}
	
}
