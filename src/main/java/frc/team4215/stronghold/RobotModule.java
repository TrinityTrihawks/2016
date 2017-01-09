
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import jaci.openrio.toast.core.Toast;
import jaci.openrio.toast.core.thread.Heartbeat;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {
	// Physical Components
    private Victor left, right, left2, right2, intakeMotor, armMotor;
    private Joystick rightStick, gameCube;
    
    // Sensors
    private Encoder encode;
    private AnalogGyro gyro;
    
    // Constants for the arm PID
    private final double  ARM_SETPOINT = .5;
    private final double ARM_KP = .02;
    private final double ARM_KI = 0;
    private final double ARM_KD = .03;
    private final boolean ARMISGO = false;
    private PIDController  armControl;
    
    // Constants for turning State Space Controller
    private final double TURN_TO = 180;
    private final double[] R = {TURN_TO,0};
    private final double TURN_KP = 3.5;
    private final double TURN_KV = 5.8;
    private final double[][] K = {{TURN_KP,TURN_KV}};
    private final boolean TURNISGO = true;
    private StateSpaceController turnControl;
    
    // Subsystems
    private DriveTrain chassis;
    private Arm arm;
    private Intake intake;
    private UI driveStation;
    private Autonomous auto;
    
    DataGather blackBox;
    
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
        intake = new Intake();
        chassis = new DriveTrain(left, left2, right, right2);
        auto = new Autonomous(chassis,arm);
        
        gyro = new AnalogGyro(0);
        gyro.reset();
        gyro.calibrate();
        
        // Sending Gyro data
        Heartbeat.add(skipped -> { if(!turnControl.isEnabled()) logger.info("Gyro:" + gyro.getAngle());});
        List<DoubleSupplier> sensors = new ArrayList();
        sensors.add(gyro::getAngle);
        sensors.add(gyro::getRate);
        
        List<DoubleConsumer> motors = new ArrayList();
        motors.add(chassis::pidWrite);
        
        // Setting a PID controller to turn the robot
        try {
			turnControl = new StateSpaceController(K,R,sensors,motors);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
        // Ticks blackbox every 100ms
        Heartbeat.add(skipped -> {blackBox.tick();});
        
        /*
         *  Encoders are'nt supported by Toast's simulation
         *  so we only activate them if not in a simulations
         */
        if(Toast.isReal()){
        	// Updates drivestation every 100ms
            Heartbeat.add(skipped -> {driveStation.giveMotorVoltages();});
            
        	// Setting up encoder and arm PID controller
        	encode = new Encoder(1,2,false);
        	encode.setDistancePerPulse(0);
        	armControl = new PIDController(ARM_KP,ARM_KI,ARM_KD,encode,arm);
        }
        blackBox = new DataGather(chassis,arm,driveStation,gyro);
    }
    
    @Override
    public void disabledInit(){
    	// Shuts down PID control
    	if(Toast.isReal()){
    		if(armControl.isEnabled()){
    			armControl.disable();
    		}
    	}
    	if(turnControl.isEnabled()){
    		turnControl.disable();
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
    	
    	if(turnControl.isEnabled()){
    		turnControl.disable();
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
    	// Turning on PID Controllers
    	if(Toast.isReal()){
    		if(ARMISGO){
    			armControl.setSetpoint(ARM_SETPOINT);
    			armControl.enable();
    			Heartbeat.add(skipped -> {
    					if(armControl.isEnabled()) logger.info("Arm Error:" + armControl.getAvgError());
    				});
    		}
    		
    	}
    	
    	if(TURNISGO){
			turnControl.enable();
			/*
			Heartbeat.add(skipped -> {
				if(turnControl.isEnabled()) logger.info("Turn error:" + turnControl.getAvgError());
			});
			*/
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
