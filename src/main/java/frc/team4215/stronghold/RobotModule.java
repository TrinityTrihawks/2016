
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    private Victor left, right, left2, right2;
    
    private DriveTrain chassis;
    private Arm arm;
    private Winch winch;
    
    private Joystick leftStick, rightStick, gameCube;

    private UI driveStation;
    
    UltraSonic ult;
    
    public static Logger logger;

    private static final String ModuleName = "stronghold";

    private static final String ModuleVersion = "0.0.1";
    
    @Override
    public String getModuleName() {
        
        return RobotModule.ModuleName;
    }
    
    @Override
    public String getModuleVersion() {
        
        return RobotModule.ModuleVersion;
    }

    /**
     * The method called during initialization.
     */
    @Override
    public void robotInit() {
        
        // leftStick = new Joystick(0);
        rightStick = new Joystick(Const.JoyStick.Num.PlayStation);
        gameCube = new Joystick(Const.JoyStick.Num.GameCube);

        driveStation = new UI(rightStick, gameCube);
        
        ult = new UltraSonic(3);
        
        // create winch
        winch = new Winch();
        
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);
        
        
        left = Registrar.victor(3);
        left2 = Registrar.victor(1);
        right = Registrar.victor(2);
        right2 = Registrar.victor(0);
        
        chassis = new DriveTrain(left,left2, right,right2);
        
        rightStick = new Joystick(1);
        
        I2CGyro.initGyro();
        I2CGyro.pingerStart();
    }
    
    public void runAccel(){
    	while(true){
    	double[] accel = I2CGyro.getAngles();
    	if(accel[0]+accel[1]+accel[2] != 0)
    		logger.info("Angles : " + accel[0] + " ," + accel[1] + " ," + accel[2]);
    	}
    }
    
    @Override
    public void teleopPeriodic() {
        gameCube.getRawButton(Const.JoyStick.Button.GameCube_Y);
        gameCube.getRawButton(Const.JoyStick.Button.GameCube_X);
        double[] inputs = driveStation.getDriveInputs();
        chassis.drive(inputs[0], inputs[1]);
    }
    
    @Override
    public void autonomousPeriodic() {

    }
    
    @Override
    public void autonomousInit() {
        winch.setSafetyEnabled(false);
        
        Autonomous.startTimer();

    }

    @Override
    public void teleopInit() {
        winch.setSafetyEnabled(true);
    }
}
