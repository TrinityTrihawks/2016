package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Timer;

/**
 * The class for Autonomous.
 *
 * @author James
 */
public class Autonomous {

    private static Thread threadPing;

    /**
     * Measured in inches.
     */
    private static double distanceTraveled;

    private static final boolean DEBUG = true;

    private static double velocityAttained;

    public static Timer time = new Timer();

    private static double accelerometerKp;

    private static double accelerometerKi;
    
    // private static double lastTime;
    
    private static double setpointGyro;
    private static double setpointAccel;
    private static double errSumGyro;
    private static double errSumAccel;

    private static double gyroKp = .12, gyroKi = .01;
    private static double lastTimeGyro;
    private static double lastTimeAccel;
    private static double lastTimeDistance;

    /**
     * Length of Autonomous period, seconds
     */
    private static final double AUTOTIME = 15;
    /**
     * Sample rate, times/second.
     */
    private static final double SAMPLINGRATE = 20;
    
    // private Victor armMotor, intake;
    
    private DriveTrain dT;
    
    private Arm arm;

    private Intake intake;

    private Winch winch;
    
    private Interface choiceAuto;
    
    private Autonomous auto;

    double input;
    
    public Autonomous(DriveTrain dT_) {
        dT = dT_;
        arm = new Arm();
        intake = new Intake();
        winch = new Winch();
    }
    
    boolean first = true;
    boolean backwards = false;
    double then = 0;
    public void childsPlay(){
    	if(backwards){
    		if((time.get() - then) >= 3)
    			dT.drive(0);
    		else
    			dT.drive(.3);
    	}
    	else{
    		
    		if(first){
    			then = time.get();
    			first = false;
    		}
    		
    		if((time.get() - then) >= 3){
    			backwards = false;
    			then = time.get();
    		}
    		else
    			dT.drive(-.3);
    	}
    }
    
    /*
     * Actually, I highly doubt if this would work or not. If this won't
     * work I know how to fix it. - James
     */
    public void chooseAuto(int num) {
        if (num == 1) choiceAuto = this::autoLowBar;
        else if (num == 2) choiceAuto = this::autoSpyBotLowGoal;
        else if (num == 3) choiceAuto = this::autoChevalDeFrise;
        // else if (num == 4) choiceAuto = this::autoPortcullis;
        else choiceAuto = null;
    }
    
    public void autoChoice() throws RobotException {
        if (null != choiceAuto)
            throw new RobotException("There is not a method chosen.");
        choiceAuto.runAuto();
    }
    
    /**
     * PID controller implementation for accelerometer. Waweru and I have
     * decided that the derivative part for the controller is unnecessary.
     * Derivative function taken out. I moved this code directly into the
     * I2CDistanceTraveled section.
     *
     * @author Joey
     */
    public static double accelerometerPID(double input) {
        // Time since last calculation
        double now = time.get();
        double timeChange = now - lastTimeAccel;
        
        // Calculate error variables
        double error = setpointAccel - input;
        errSumAccel += error * timeChange;
        
        // Sum errors
        double accelerometerError =
                accelerometerKp * error + accelerometerKi * errSumAccel;

        // Reset time variable
        lastTimeAccel = now;
        
        return accelerometerError;
    }

    /**
     * PID controller implementation for gyroscope
     *
     * @param gyroKp
     * @param gyroKi
     * @author Joey
     */
    public double gyroPID(double input) {
        // Time since last calculation
        double now = time.get();
        double timeChange = now - lastTimeGyro;
        
        // Calculate error variables
        double error = setpointGyro - input;
        errSumGyro += error * timeChange;
        
        // Sum errors
        double gyroOutput = gyroKp * error + gyroKi * errSumGyro;
        
        // Reset time variable
        lastTimeGyro = now;
        
        return gyroOutput;
    }
    
    /**
     * Timer method integrating the Timer class from wpilibj. USE THIS
     * TIMER UNIVERSALLY!!!!!
     *
     * @author Jack Rausch
     */
    public static void startTimer() {
        time.start();
    }
    
    /**
     * All constants.
     *
     * @author James
     */
    private static final class Constant {
        
        /**
         * Constant shared.
         *
         * @author James
         */
        public static final class Shared {
            
            public static final double armMoveMaxTime = 2d;
            
            public static final double armDown = -1d, armStop = 0d;

            public static final double intakeDelay = 1d;
        }
        
        /**
         * Constant for autoLowBar.
         *
         * @author James
         */
        private static final class LowBar {
            
            public static final double driveThroughDistance = 500d;
        }
        
        /**
         * Constant for autoSpyBotLowGoal.
         *
         * @author James
         */
        private static final class SpyBotLowGoal {
            
            public static final double driveToDistance = 500d;
        }
        
        /**
         * Constant for autoChevalDeFrise.
         *
         * @author James
         */
        private static final class ChevalDeFrise {
            
            public static final double driveToDistance = 500d;
            public static final double driveThroughDistance = 500d;
        }
    }
    
    /**
     * The interface for programs outside to run the chosen autonomous
     * function.
     *
     * @author James
     */
    public interface Interface {
        
        public void runAuto();
    }
    
    /**
     * To lower arm. Need more info.
     *
     * @author James
     */
    private void armLowerBottom() {
        arm.set(Constant.Shared.armDown);
        Timer.delay(Constant.Shared.armMoveMaxTime);
        arm.set(Constant.Shared.armStop);
    }
    
    /**
     * To lift arm. Need more info
     *
     * @author James
     */
    @SuppressWarnings("unused")
    @Deprecated
    private void armLifterTop() {
        return;
        // arm.set(Constant.Shared.armUp);
        // Timer.delay(Constant.Shared.armMoveMaxTime);
        // arm.set(Constant.Shared.armStop);
    }
    
    /**
     * To drive straight.
     *
     * @author James
     * @param moveDistance
     *            Meters of required distance.
     */
    
    private double pidTurn(double target) {
        setpointGyro = target;
        
        // Input for the pid loop
        double[] angles = I2CGyro_getAngles();
        
        double input = gyroPID(angles[2]);

        input = Math.atan((Math.PI / 2) * input); // function with a curve
                                                  // like the error curve

        return input;
    }
    
    private void driveStraight(double target) {
        double out = pidTurn(target);
        dT.drive(-out, out);
    }
    
    double errSum = 0;
    double lastTime = 0;
    double distanceTraveledkp = .009;
    double distanceTraveledki = 0;
    double distanceTraveledkd;
    double outPut;

    /**
     * This function takes the Distance traveled over a given amount of
     * time and sets the voltage Thanks to Jack for the prototype code
     *
     * @param setPoint
     * @author Ransom
     */
    public double distancePid(double setPoint) {
        // How long since we last calculated
        double now = time.get();
        double timeChange = now - lastTime;

        // Compute all the working error variables
        double error = setPoint - distanceTraveled;
        errSum += (error * timeChange);

        // Compute PID Output
        outPut = distanceTraveledkp * error + distanceTraveledki * errSum;

        // Normalizes the Output between -1 and 1
        outPut = 2 / Math.PI * Math.atan(outPut);

        // Uses Output to drive
        dT.drive(outPut);

        // Saved for next calculation
        lastTime = now;
        if (DEBUG) {
            RobotModule.logger.info("Output: " + outPut);
            RobotModule.logger.info("Distance: " + distanceTraveled);
        }
        return outPut;
    }
    
    public double distanceTraveled() {
        return distanceTraveled;
    }

    /**
     * Drives forward while turning
     *
     * @param x
     * @param y
     * @author Ransom
     */
    public void driveToPoint(double x, double y) {
        double theta = Math.atan(x / y);
        double r = Math.pow(x, 2) + Math.pow(y, 2);
        RobotModule.logger.info("The angle is" + theta);
        RobotModule.logger.info("The distance is" + r);
        if (theta - I2CGyro.getAngles()[2] < .5) {
            dT.drive((distancePid(r) + pidTurn(theta)),
                    distancePid(r) + pidTurn(theta));
            if (DEBUG) RobotModule.logger
                    .info("Left: " + -(distancePid(r) + pidTurn(theta))
                            + "Right: " + -(distancePid(r) + pidTurn(theta)));
        } else {
            dT.drive(-pidTurn(theta), pidTurn(theta));
            RobotModule.logger
                    .info("Turning: " + -pidTurn(theta) + pidTurn(theta));
        }
    }

    /**
     * Throws ball out. Yet to be tested.
     *
     * @author James
     */
    private void throwBall() {
        intake.set(Const.Motor.Run.Forward);
        Timer.delay(Constant.Shared.intakeDelay);
        intake.set(Const.Motor.Run.Stop);
    }

    public void winchInit() {
    	winch.setSafetyEnabled(false);
    	
        winch.set(Const.Motor.Run.WinchInitSpeed);
        Timer.delay(Const.Motor.Run.WinchInitTime);
        winch.set(Const.Motor.Run.WinchStop);
        
        winch.setSafetyEnabled(true);
        
    }
    
    /**
     * Autonomous function No.1
     *
     * @author James
     */
    public void autoLowBar() {
        winchInit();
        armLowerBottom();
        driveStraight(Constant.LowBar.driveThroughDistance);
    }
    
    /**
     * Autonomous function No.2
     *
     * @author James
     */
    public void autoSpyBotLowGoal() {
        winchInit();
        armLowerBottom();
        driveStraight(Constant.SpyBotLowGoal.driveToDistance);
        throwBall();
    }
    
    /**
     * Autonomous function No.3
     *
     * @author James
     */
    public void autoChevalDeFrise() {
        winchInit();
        driveStraight(Constant.ChevalDeFrise.driveToDistance);
        armLowerBottom();
        driveStraight(Constant.ChevalDeFrise.driveThroughDistance);
    }
    
    /**
     * Autonomous function No.4, not used.
     *
     * @author James
     */
    @Deprecated
    public void autoPortcullis() {
        throw null;
        // armLowerBottom();
        // driveStraight(Constant.Portcullis.driveToDistance);
        // armLifterTop();
        // driveStraight(Constant.Portcullis.driveThroughDistance);
    }
    
    /**
     * Should be equivalent to a method called getAccel of another class
     * I2CAccelerometer which isn't here yet.
     *
     * @author James
     * @return Accelerations, double[] with length of 3
     */
    @SuppressWarnings("unused")
    @Deprecated
    private static double[] I2CAccel_getAccel() {
        // Ok, it seems like this function is not used?
        return I2CAccel.getAccel();
    }
    
    /**
     * Calculates distance traveled based on information from the
     * accelerometer. The distanceTraveled is measured in inches.
     *
     * @author Joey
     */
    private void I2CDistanceTraveled() {
        
        for (int count = 0; count < (AUTOTIME * SAMPLINGRATE); count++) {

            Timer.delay(1 / SAMPLINGRATE);
            double time2 = time.get();
            double timeChange = time2 - lastTimeDistance;
            lastTimeDistance = time2;
            double[] acceleration = I2CAccel.getAccel();
            
            velocityAttained += acceleration[1] * timeChange;
            
            distanceTraveled += velocityAttained * timeChange;
            
        }
    }

    public void pingerStart() {
        Runnable pinger = () -> {
            while (true)
                I2CDistanceTraveled();
        };
        
        threadPing = new Thread(pinger);
        lastTimeDistance = time.get();
        threadPing.start();
        
    }
    
    public void timeBasedLowBarAuto() {
        dT.setSafetyEnabled(false);
        arm.setSafetyEnabled(false);
        
        arm.set(-.25);
        Timer.delay(.5);
        arm.set(0);
        
        dT.drive(.5,.45);
        Timer.delay(5);
        dT.drive(0);
        Timer.delay(.5);
        dT.drive(-.5,-.45);
        Timer.delay(4);
        dT.drive(0);
        
        return;
        
    }
    
    /**
     * Should be equivalent to a method called getAngles of another class
     * I2CGyro which isn't here yet.
     *
     * @author James
     * @return Angles, double[] with length of 3
     * @see {@link I2CGyro#getAngles()}
     */
    private static double[] I2CGyro_getAngles() {
        return I2CGyro.getAngles();
    }

}
