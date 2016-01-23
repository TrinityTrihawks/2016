package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Intake {
    private Joystick GameCube;
    private Victor Intake;

    public Intake() {
        this.GameCube = new Joystick(Const.JoyStick.GameCube);
        this.Intake = new Victor(Const.Motor.Num.Intake);
    }

    public void Run() {
        if (this.GameCube.getRawButton(Const.JoyStick.Button.GameCube_A))
            this.Intake.set(Const.Motor.Run.Forward);
        else if (this.GameCube.getRawButton(Const.JoyStick.Button.GameCube_B))
            this.Intake.set(Const.Motor.Run.Backward);
        else
            this.Intake.set(Const.Motor.Run.Stop);
    }
}
