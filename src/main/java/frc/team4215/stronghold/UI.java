package frc.team4215.stronghold;

import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI {
	Joystick leftStick;
	Joystick rightStick;
	String DashStr1;
	String DashStr2;
	String DashStr3;
	String DashStr4;
	String DashStr5;
	Victor leftMotor;
	Victor rightMotor;
	Victor rightMotor2;
	Victor leftMotor2;
	Victor intake;
	Victor arm;
	
	List<String> myvolts = new ArrayList<>();
	List<Double> atvolts = new ArrayList<>();
	
	List<String> getVoltages(){
		String lMotor1 = String.valueOf(leftMotor.get());
		String lMotor2 = String.valueOf(leftMotor2.get());
		String rMotor1 = String.valueOf(rightMotor.get());
		String rMotor2 = String.valueOf(rightMotor2.get());
		myvolts.add(lMotor1);
		myvolts.add(lMotor2);
		myvolts.add(rMotor1);
		myvolts.add(rMotor2);
		return myvolts;
	}
	
	List<Double> getAttachmentsVoltages(){
		double inMotor = intake.get();
		double armMotor = arm.get();
		atvolts.add(inMotor);
		atvolts.add(armMotor);
		return atvolts;
		
	}
	
	public void giveMotorVoltages(){
		SmartDashboard.putString("Drive Motor 1", "Leftmotor1 volts: " + myvolts.get(0));
		SmartDashboard.putString("Drive Motor 2", "Leftmotor2 volts: " + myvolts.get(1));
		SmartDashboard.putString("Drive Motor 3", "Rightmotor1 volts: " + myvolts.get(2));
		SmartDashboard.putString("Drive Motor 4", "Rightmotor2 volts: " + myvolts.get(3));
		SmartDashboard.putNumber("Intake Motor", atvolts.get(0));
		SmartDashboard.putNumber("Arm Motor", atvolts.get(1));
		
	}

	Joystick[] driveSticks = new Joystick[2];
	Joystick thirdstick;

	public UI(Joystick leftStick_,Joystick rightStick_, Joystick thirdstick_, Victor leftmotor_, Victor rightmotor_,Victor rightmotor2_,Victor leftmotor2_,Victor intake_,Victor arm_){
		driveSticks[0] = leftStick_;
		driveSticks[1] = rightStick_;
		thirdstick = thirdstick_;
		leftMotor = leftmotor_;
		rightMotor = rightmotor_;
		leftMotor2 = leftmotor2_;
		rightMotor2 = rightmotor2_;
		intake = intake_;
		arm = arm_;
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
	
	
	
	

 