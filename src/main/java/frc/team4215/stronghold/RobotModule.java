
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import jaci.openrio.toast.core.Toast;
import jaci.openrio.toast.core.thread.Heartbeat;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {

    private Victor left, right, left2, right2, intakeMotor, armMotor;
    
    double setPoint = .5;
    private double Kp = .1;
    private double Ki = 0;
    private double Kd = 0;
    private boolean PIDISGO = false;
    
    private DriveTrain chassis;
    private Arm arm;
    private Winch winch;
    private Intake intake;
    
    private Joystick rightStick, gameCube;

    private UI driveStation;
    private Encoder encode;
    
    Autonomous auto;
    
    DataGather blackBox;
    
    PIDController  armControl;
    
    Timer timer = new Timer();
    
    public static Logger logger;

    private static final String ModuleName = "stronghold";

    private static final String ModuleVersion = "0.1.0";
    
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

        // Declaring Joysticks
        rightStick = new Joystick(Const.JoyStick.Num.PlayStation);
        gameCube = new Joystick(Const.JoyStick.Num.GameCube);
        
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);
        
        // Motors, Glorious motors
        left = Registrar.victor(3);
        left2 = Registrar.victor(1);
        right = Registrar.victor(2);
        right2 = Registrar.victor(0);
        intakeMotor = Registrar.victor(5);
        armMotor = Registrar.victor(4);
        		
        // All of the objects for the different subsystems of the robot
        driveStation = new UI(rightStick, gameCube, left, right, right2,left2,intakeMotor,armMotor);
        arm = new Arm();
        winch = new Winch();
        intake = new Intake();
        chassis = new DriveTrain(left, left2, right, right2);
        auto = new Autonomous(chassis,arm);
        blackBox = new DataGather(chassis,arm,driveStation);
        
        // Ticks blackbox every 100ms
        Heartbeat.add(skipped -> {blackBox.tick();});
        
        
        if(Toast.isReal()){
        	// Updates drivestation every 100ms
            Heartbeat.add(skipped -> {driveStation.giveMotorVoltages();});
            
        	// Setting up encoder and PID controller
        	encode = new Encoder(1,2,false);
        	encode.setDistancePerPulse(0);
        	armControl = new PIDController(Kp,Ki,Kd,encode,arm);
        }
    }
    
    @Override
    public void disabledInit(){
    	// Shuts down PID control
    	if(Toast.isReal()){
    		if(armControl.isEnabled()){
    			armControl.disable();
    		}
    	}
    }
    
    @Override
    public void teleopInit(){
    	
    	// Shutting down PID controller
    	if(Toast.isReal()){
    		if(armControl.isEnabled()){
    			armControl.disable();
    		}
    	}
    	
    	// Making the drivetrain and arm safe
    	chassis.setSafetyEnabled(true);
    	arm.setSafetyEnabled(true);
    	
    }
    
    // Declaring the array outside so it isn't reallocated every time
    double[] inputs;
    
    @Override
    public void teleopPeriodic() {
        
        inputs = driveStation.getDriveInputs();
        chassis.drive(-inputs[0], -inputs[1]);
        arm.Run();
        intake.Run();
        
    }
    
    @Override
    public void autonomousInit(){
    	auto.moatChevalAuto();
    	
    	if(Toast.isReal() && PIDISGO){
    		armControl.setSetpoint(setPoint);
    		armControl.enable();
    		Heartbeat.add(skipped -> {if(armControl.isEnabled()) logger.info("Error:" + armControl.getAvgError());});
    	}
    	
    }
    
    @Override
    public void testInit(){
    	if(Toast.isReal())
    		if(armControl.isEnabled()){
    			armControl.disable();
    		}
    }
}
