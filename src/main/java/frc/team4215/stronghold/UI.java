package frc.team4215.stronghold;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class UI {
	Joystick leftStick;
	Joystick rightStick;
	String DashStr1;
	String DashStr2;
	String DashStr3;
	String DashStr4;
	String DashStr5;
	Talon leftMotor;
	Talon rightMotor;
	Talon rightMotor2;
	Talon leftMotor2;
	
	List<String> getVoltages(){
		String lMotor1 = String.valueOf(leftMotor.get());
		String lMotor2 = String.valueOf(leftMotor2.get());
		String rMotor1 = String.valueOf(rightMotor.get());
		String rMotor2 = String.valueOf(rightMotor2.get());
		List<String> myvolts = new ArrayList<>();
		myvolts.add(lMotor1);
		myvolts.add(lMotor2);
		myvolts.add(rMotor1);
		myvolts.add(rMotor2);
		return myvolts;
	}
	

	UI(Joystick leftStick_,Joystick rightStick_){
		leftStick = leftStick_;
		rightStick = rightStick_;
	}
	
	double[] getInputs(){
		double[] inputs = {
		leftStick.getRawAxis(1),
		rightStick.getRawAxis(1)
		};
		return inputs;
	}
	
}
