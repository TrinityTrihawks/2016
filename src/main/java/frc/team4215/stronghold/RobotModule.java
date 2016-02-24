
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    private Victor left, right, left2, right2;
    
    private DriveTrain chassis;
    private Arm arm;
    private Winch winch;
    
    private Joystick rightStick, gameCube;

    private UI driveStation;
    
    UltraSonic ult;
    
    Autonomous auto;
    
    DataGather blackBox;
    
    Timer timer = new Timer();
    
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
        
        arm = new Arm();
        chassis = new DriveTrain(left, left2, right, right2);
        auto = new Autonomous(chassis);
        blackBox = new DataGather(chassis);
        
        // Starting 
        I2CGyro.initGyro();
        I2CGyro.pingerStart();
        I2CAccel.initAccel();
        I2CAccel.pingerStart();
        auto.pingerStart();
    }
    
    @Override
    public void teleopPeriodic() {
    	/*
    	 * A quick series of if statements
    	 *  to set the winch
    	 */
    	
        if (gameCube.getRawButton(Const.JoyStick.Button.GameCube_Y))
           auto.winchInit();
        else
        	winch.set(0);
        
        arm.changeState(gameCube.getRawButton(6));
        chassis.setState(rightStick.getRawButton(6));
        
        double[] inputs = driveStation.getDriveInputs();
        chassis.drive(inputs[0], inputs[1]);
        arm.Run();
        blackBox.tick();
    }
    
    @Override
    public void autonomousInit() {
       auto.time.reset();;
       auto.time.start();
    }
    
    @Override
    public void autonomousPeriodic(){
    	double output = auto.distancePid(60);
    	chassis.drive(-output);
    	
    	blackBox.tick();
    	
    }
    
    @Override
    public void teleopInit() {
        winch.setSafetyEnabled(true);
    }
    
    @Override
    public void testPeriodic(){
    	double[] inputs = driveStation.getDriveInputs();
    	chassis.setIndependently( inputs[0],inputs[0],inputs[1],inputs[1]);
    }
    
}
