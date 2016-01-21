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
	
	
	
	
}
 