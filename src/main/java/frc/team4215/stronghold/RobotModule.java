
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
    
    private Joystick leftStick, rightStick, gameCube;

    private UI driveStation;
    
    UltraSonic ult;
    
    Autonomous auto;
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

        chassis = new DriveTrain(left, left2, right, right2);
        
        rightStick = new Joystick(1);
        auto = new Autonomous(chassis);
        
        I2CGyro.initGyro();
        I2CGyro.pingerStart();
    }
    
    public void runAccel() {
        while (true) {
            double[] accel = I2CGyro.getAngles();
            if (accel[0] + accel[1] + accel[2] != 0)
                logger.info("Angles : " + accel[0] + " ," + accel[1]
                        + " ," + accel[2]);
        }
    }
    
    @Override
    public void teleopPeriodic() {
    	/*
    	 * A quick series of if statements
    	 *  to set the winch
    	 */
        if (gameCube.getRawButton(Const.JoyStick.Button.GameCube_Y))
           auto.winchInit();
        
        double[] inputs = driveStation.getDriveInputs();
        chassis.drive(inputs[0], inputs[1]);
    }
    
    @Override
    public void autonomousInit() {
        winch.setSafetyEnabled(false);
        auto.winchInit();
    }

    @Override
    public void teleopInit() {
        winch.setSafetyEnabled(true);
    }
    
    @Override
    public void testInit(){
    	double[] inputs = new double[3];
    	
    	logger.warn("Starting Calibration in five seconds!!!!");
    	Timer.delay(5);
    	
    	logger.warn("Max out the foward motion on the "+  System.lineSeparator() + 
    				"left joystick on the drive contoller");
    	timer.start();
    	
    	while(timer.get() < 10000){
    		inputs = driveStation.getDriveInputs();
    		chassis.driveNonScaled(inputs[0], 0);
    	}
    	
    	logger.warn("Max out the backward motion on the "+  System.lineSeparator() +
					"left joystick on the drive contoller");
    	timer.reset();
    	timer.start();
    	
    	while(timer.get() < 10000){
    		inputs = driveStation.getDriveInputs();
    		chassis.driveNonScaled(inputs[0], 0);
    	}
    	
    	logger.warn("Max out the foward motion on the " + System.lineSeparator() + 
					"right joystick on the drive contoller");
    	
    	timer.reset();
    	timer.start();
    	
    	while(timer.get() < 10000){
    	inputs = driveStation.getDriveInputs();
    	chassis.driveNonScaled(0, inputs[1]);
    	}
    	
    	logger.warn("Max out the backward motion on the " +  System.lineSeparator() + 
					"right joystick on the drive contoller");
    	timer.reset();
    	timer.start();
    	while(timer.get() > 10000){
    	inputs = driveStation.getDriveInputs();
    	chassis.driveNonScaled(0, inputs[1]);
    	}
    	
    	
    }
}
