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
    }

    public void setSafetyEnabled(boolean bool) {
        winch.setSafetyEnabled(bool);
    }
    
    public void set(double value) {
        winch.set(value);
    }

    public Victor getWinch() {
        return winch;
    }
}
