package frc.team4215.stronghold;

import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI {
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
