package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.registry.Registrar;

public class Winch {

    private Victor winch;
    private Joystick gameCube;
    private double coeff = .5;
    public Winch() {
        winch = Registrar.victor(Const.Motor.Num.Winch);
        gameCube = new Joystick(Const.JoyStick.Num.GameCube);
    }

    public void setSafetyEnabled(boolean bool) {
        winch.setSafetyEnabled(bool);
    }
    
    public void run(){
    	
     double val = gameCube.getRawAxis(Const.JoyStick.Button.GameCube_Y);
     winch.set(coeff*val);
    		
    }
    public void set(double value) {
        winch.set(value);
    }

    public Victor getWinch() {
        return winch;
    }
}
