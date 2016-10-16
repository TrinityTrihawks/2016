package frc.team4215.stronghold;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4215.stronghold.Autonomous;

public class UI {
    
    private Command autoCom;
    private SendableChooser autoChoose = new SendableChooser();
    private static final String LOWBAR = "Low Bar";
    private static final String SPYBOT = "SpyBot";
    private static final String CHIVALDEFRISE = "Chival de Frise";
    private static final String PORTCULLIS = "Portcullis";
    private static final String CHOOSEAUTOMODE =
            "Choose Autonomous Mode";
    private Autonomous auto;
    
    public void makeModes() {
        autoChoose.addObject(UI.LOWBAR, new Integer(1));
        autoChoose.addObject(UI.SPYBOT, new Integer(2));
        autoChoose.addObject(UI.CHIVALDEFRISE, new Integer(3));
        autoChoose.addObject(UI.PORTCULLIS, new Integer(4));
        
        SmartDashboard.putData(UI.CHOOSEAUTOMODE, autoChoose);
        // DOES THE SD NEED TO PROMPT A QUESTION BOX?
        
        Integer num = (Integer) autoChoose.getSelected();
        this.choose(num);
    }
    
    private ArrayList<Joystick> driveSticks =
            new ArrayList<Joystick>();
    private Joystick thirdstick;
    
    public UI(Joystick leftStick_, Joystick rightStick_,
            Joystick thirdstick_, Victor leftmotor_,
            Victor rightmotor_, Victor rightmotor2_,
            Victor leftmotor2_, Victor intake_, Victor arm_) {
        driveSticks.add(leftStick_);
        driveSticks.add(rightStick_);
        thirdstick = thirdstick_;
        frontLeftMotor = leftmotor_;
        frontRightMotor = rightmotor_;
        backLeftMotor = leftmotor2_;
        backRightMotor = rightmotor2_;
        intake = intake_;
        arm = arm_;
    }

    public UI(Joystick leftStick_, Joystick thirdstick_,
            Victor leftmotor_, Victor rightmotor_,
            Victor rightmotor2_, Victor leftmotor2_, Victor intake_,
            Victor arm_) {
        driveSticks.add(leftStick_);
        thirdstick = thirdstick_;
        frontLeftMotor = leftmotor_;
        frontRightMotor = rightmotor_;
        backLeftMotor = leftmotor2_;
        backRightMotor = rightmotor2_;
        intake = intake_;
        arm = arm_;
    }
    
    public void choose(int num) {
    	// Well as long as no one  this, it should work
    	
        //auto.chooseAuto(num);
    }

    public void choose(Integer num) {
        this.choose(num.intValue());
    }
    double[] inputs = new double[2];;
    double[] getDriveInputs() {
        if (driveSticks.size() == 1) {
            inputs[0] = driveSticks.get(0).getRawAxis(1);
            inputs[1] = driveSticks.get(0).getRawAxis(5);
        } else {
            inputs[0] = driveSticks.get(0).getRawAxis(1);
            inputs[1] = driveSticks.get(1).getRawAxis(1);
        }
        return inputs;
    }

    double[] getArmInput() {
        return new double[] { thirdstick
                .getRawAxis(Const.JoyStick.Axis.GameCubeCtrl_UD) };
    }
    
    public Joystick leftStick, rightStick;
    public String dashStr1, dashStr2, dashStr3, dashStr4, dashStr5;
    public Victor frontLeftMotor, backLeftMotor, backRightMotor,
            frontRightMotor, intake, arm;
            
    public List<Double> atVolts = new ArrayList<>();
    
    public List<String> getVoltages() {
    	List<String> myVolts = new ArrayList<>();
        String lMotor1 = String.valueOf(frontLeftMotor.get());
        String lMotor2 = String.valueOf(backLeftMotor.get());
        String rMotor1 = String.valueOf(backRightMotor.get());
        String rMotor2 = String.valueOf(frontRightMotor.get());
        myVolts.add(lMotor1);
        myVolts.add(lMotor2);
        myVolts.add(rMotor1);
        myVolts.add(rMotor2);
        return myVolts;
    }
    
    public double[] getAttachmentsVoltages() {
        double inMotor = intake.get();
        double armMotor = arm.get();
        double[] atVolts = { inMotor, armMotor };
        return atVolts;
    }
    
    public void giveMotorVoltages() {
    	List<String> myVolts = getVoltages();
    	double[] atVolts = getAttachmentsVoltages();
        SmartDashboard.putString("Drive Motor 1",
                "Leftmotor1 volts: " + myVolts.get(0));
        SmartDashboard.putString("Drive Motor 2",
                "Leftmotor2 volts: " + myVolts.get(1));
        SmartDashboard.putString("Drive Motor 3",
                "Rightmotor1 volts: " + myVolts.get(2));
        SmartDashboard.putString("Drive Motor 4",
                "Rightmotor2 volts: " + myVolts.get(3));
        SmartDashboard.putNumber("Intake Motor", atVolts[0]);
        SmartDashboard.putNumber("Arm Motor", atVolts[1]);
        
    }
    
    public UI() {
    }
    
    public UI(Joystick left, Joystick thirdstick) {
        this();
        driveSticks.add(left);
        this.thirdstick = thirdstick;
    }
    
}
