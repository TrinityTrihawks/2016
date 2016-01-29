package frc.team4215.stronghold;

import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4215.stronghold.Autonomous;

public class UI {

	Command autoCom;
	Sendable autoChoose = new SendableChooser();
	public void makeModes(){
		this.autoChoose.addDefault("LowBar", new autoLowBar());
		this.autoChoose.addObject("Portcullis", new autoPortcullis());
		this.autoChoose.addObject("ChevalDeFrise", new autoChevalDeFrise());
		this.autoChoose.addObject("SpyBotLowGoal", new autoSpyBotLowGoal());
		SmartDashboard.putData("Choose Autonomous Mode", autoChoose);
	}
	
	
	Joystick[] driveSticks = new Joystick[2];
	Joystick thirdstick;

	public UI(Joystick leftStick_,Joystick rightStick_, Joystick thirdstick_, Victor leftmotor_, Victor rightmotor_,Victor rightmotor2_,Victor leftmotor2_,Victor intake_,Victor arm_){
		driveSticks[0] = leftStick_;
		driveSticks[1] = rightStick_;
		thirdstick = thirdstick_;
		frontLeftMotor = leftmotor_;
		frontRightMotor = rightmotor_;
		backLeftMotor = leftmotor2_;
		backRightMotor = rightmotor2_;
		intake = intake_;
		arm = arm_;
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
	

	
    public Joystick leftStick, rightStick;
    public String dashStr1, dashStr2, dashStr3, dashStr4, dashStr5;
    public Victor frontLeftMotor, backLeftMotor, backRightMotor, frontRightMotor, intake, arm;

    public List<String> myVolts = new ArrayList<>();
    public List<Double> atVolts = new ArrayList<>();

    public List<String> getVoltages() {
        String lMotor1 = String.valueOf(this.frontLeftMotor.get());
        String lMotor2 = String.valueOf(this.backLeftMotor.get());
        String rMotor1 = String.valueOf(this.backRightMotor.get());
        String rMotor2 = String.valueOf(this.frontRightMotor.get());
        this.myVolts.add(lMotor1);
        this.myVolts.add(lMotor2);
        this.myVolts.add(rMotor1);
        this.myVolts.add(rMotor2);
        return this.myVolts;
    }

    public List<Double> getAttachmentsVoltages() {
        double inMotor = this.intake.get();
        double armMotor = this.arm.get();
        this.atVolts.add(inMotor);
        this.atVolts.add(armMotor);
        return this.atVolts;

    }

    public void giveMotorVoltages() {
        SmartDashboard.putString("Drive Motor 1", "Leftmotor1 volts: " + this.myVolts.get(0));
        SmartDashboard.putString("Drive Motor 2", "Leftmotor2 volts: " + this.myVolts.get(1));
        SmartDashboard.putString("Drive Motor 3", "Rightmotor1 volts: " + this.myVolts.get(2));
        SmartDashboard.putString("Drive Motor 4", "Rightmotor2 volts: " + this.myVolts.get(3));
        SmartDashboard.putNumber("Intake Motor", this.atVolts.get(0));
        SmartDashboard.putNumber("Arm Motor", this.atVolts.get(1));

    }

    public UI() {
    }

    public UI(Joystick left, Joystick right) {
        this();
        this.leftStick = left;
        this.rightStick = right;
    }

}
