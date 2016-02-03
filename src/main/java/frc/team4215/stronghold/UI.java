
package frc.team4215.stronghold;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;

public class UI {

	ArrayList<Joystick> driveSticks = new ArrayList<Joystick>();
	Joystick thirdstick;

	public UI(Joystick leftStick_,Joystick rightStick_, Joystick thirdstick_){
		driveSticks.add(leftStick_);
		driveSticks.add(rightStick_);
		thirdstick = thirdstick_;
	}
	
	public UI(Joystick driveStick_, Joystick thirdstick_){
		driveSticks.add(driveStick_);
		thirdstick = thirdstick_;
	}
	
	double[] getInputs(){
		double[] inputs = new double[2];
		if(driveSticks.size() == 1){
			inputs[0] = driveSticks.get(0).getRawAxis(1);
			inputs[1] = driveSticks.get(0).getRawAxis(3);
		}
		else if(driveSticks.size() == 2){
			inputs[0] = driveSticks.get(0).getRawAxis(1);
			inputs[1] = driveSticks.get(1).getRawAxis(1);
		}
		return inputs;
	}	

}
