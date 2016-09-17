
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    private Victor left, right, left2, right2;
    
    double setPoint = .5;
    double lastPoint;
    double lastTime;
    double sum;
    
    double Kp = .1;
    double Ki = 0;
    double Kd = 0;
    
    private DriveTrain chassis;
    private Arm arm;
    private Winch winch;
    private Intake intake;
    
    private Joystick rightStick, gameCube;

    private UI driveStation;
    private Encoder encode;
    
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
        
        // create winch
        winch = new Winch();
        intake = new Intake();
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);

        left = Registrar.victor(3);
        left2 = Registrar.victor(1);
        right = Registrar.victor(2);
        right2 = Registrar.victor(0);
        
        arm = new Arm();
        chassis = new DriveTrain(left, left2, right, right2);
        auto = new Autonomous(chassis);
        blackBox = new DataGather(chassis,arm,driveStation);
        
        encode = new Encoder(1,2,false);
        encode.setDistancePerPulse(360);

    }
    
    @Override
    public void disabledInit(){
    	
    	/*
    	 *  Stop the sensors to try
    	 *  to mitagate Sensor drift and
    	 *  power usage
    	 */
    	
    	//I2CGyro.pingerStop();
    	//I2CAccel.pingerStop();
    }
    
    @Override
    public void teleopInit(){
    	/*
    	 * Start the sensors
    	 */
    	/*
    	 * Make safe the motors
    	 */
    	chassis.setSafetyEnabled(true);
    	arm.setSafetyEnabled(true);
    	
    }
    
    @Override
    public void teleopPeriodic() {
    	/*
    	 * Runs the winch code when a button's pressed
    	 * 
    	 */
    	
        if (gameCube.getRawButton(Const.JoyStick.Button.GameCube_Y))
           auto.winchInit();
        else
        	winch.set(0);
        
        arm.changeState(gameCube.getRawButton(6));
        chassis.setState(rightStick.getRawButton(6));
        
        double[] inputs = driveStation.getDriveInputs();
        chassis.drive(-inputs[0], -inputs[1]);
        arm.Run();
        intake.Run();
        
        blackBox.tick();
    }
    
    @Override
    public void autonomousInit(){
    	sum = 0;
    	lastTime = 0;
    	
    	timer.reset();
    	
    }
    	
    @Override
    public void autonomousPeriodic(){
    	double error = setPoint - encode.getDistance();
    	double time = timer.get();
    	double dt = time - lastTime;
    	lastTime = time;
    	sum = sum + dt*error;
    	
    	double pi = Kp*error + Ki*sum;
    	
    	arm.set(pi);
    }
    
    
    @Override
    public void testPeriodic(){
    	double[] inputs = driveStation.getDriveInputs();
    	
    	chassis.setIndependently( inputs[0],inputs[0],inputs[1],inputs[1]);
    }
    
    
    @Override
    public void disabledPeriodic(){
    	blackBox.tick();
    }
}
