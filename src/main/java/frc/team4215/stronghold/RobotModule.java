package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.registry.Registrar;

public class RobotModule extends IterativeModule {
	
	Talon left;
    Talon right;
    Talon left2;
    Talon right2;
    
    Ultrasonic ultra = new Ultrasonic(1,1);
    
    
    DriveTrain chassis;
    
    Joystick leftStick;
    Joystick rightStick;
    Victor leftMotor;
	Victor rightMotor;
	Victor rightMotor2;
	Victor leftMotor2;
	Victor intake;
	Victor arm;
    
    UI driveStation;
    
    UltraSonic ult;
    
    public static Logger logger;
    private static String ModuleName = 
        "stronghold";
    private static String ModuleVersion = 
        "0.0.1";

    @Override
    public String getModuleName() {
        return ModuleName;
    }

    @Override
    public String getModuleVersion() {
        return ModuleVersion;
    }

    @Override
    public void robotInit() {
        logger = new Logger("stronghold", Logger.ATTR_DEFAULT);

        left = Registrar.talon(0);
        right = Registrar.talon(1);
        left2 = Registrar.talon(2);
        right2 = Registrar.talon(3);
        
        chassis = new DriveTrain(left,right,left2,right2);//TODO: Module Init
        
        chassis = new DriveTrain(left,right,left2,right2);
        
        leftStick = new Joystick(0);
        rightStick = new Joystick(1);
        
//        public void drivingMethod(){
//        	joystick.getRawAxis(5)	
        
//        	double Axis1 = leftStick.getRawAxis(1);
//        	double Axis5 = rightStick.getRawAxis(5);
        
        driveStation = new UI(leftStick,rightStick,leftStick, leftMotor,rightMotor,leftMotor2,rightMotor2,intake,arm);
        
        ult = new UltraSonic(1);
        logger.info("Sensing ");
    }
    
    @Override
    public void teleopPeriodic(){
    	double[] inputs = driveStation.getInputs();
    	chassis.drive(inputs[0], inputs[1]);
    	logger.info("Sensing " + ult.getRangeInch());
    }
} 
   