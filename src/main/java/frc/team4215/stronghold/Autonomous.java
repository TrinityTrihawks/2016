package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;

/**
 * The class for Autonomous.
 *
 * @author James
 */
public class Autonomous {
    
    private static Thread threadPing;
    private static double position;
    /**
     * Seconds of Autonomous period
     */
    private static final double AUTOTIME = 15;
    /**
     * Sample rate, times/second.
     */
    private static final double SAMPLINGRATE = 20;
    private javax.management.timer.Timer timer;

    // private Victor armMotor, intake;

    private DriveTrain dT;

    private Arm arm;
    
    private Intake intake;

    private Interface choiceAuto;

    public Autonomous(DriveTrain dT_) throws RobotException {
        this.dT = dT_;
        this.arm = new Arm();
        this.intake = new Intake();
    }

    public void chooseAuto(int num) {
        if (num == 1) this.choiceAuto = () -> this.autoLowBar();
        else if (num == 2)
            this.choiceAuto = () -> this.autoSpyBotLowGoal();
        else if (num == 3)
            this.choiceAuto = () -> this.autoChevalDeFrise();
        else if (num == 4)
            this.choiceAuto = () -> this.autoPortcullis();
        else this.choiceAuto = null;
    }

    public void autoChoice() throws RobotException {
        if (null != this.choiceAuto)
            throw new RobotException("There is not a method chosen.");
        this.choiceAuto.runAuto();
    }

    /* working variables */
    private double lastTime;
    private double Input, Output, Setpoint;
    private double errSum, lastErr;
    private double kp, ki, kd;

    /**
     * PID controller
     *
     * @author Jack Rausch
     */
    public double errorCompute() {
        // How long since we last calculated
        double now = this.getTime();
        double timeChange = now - this.lastTime;

        // Compute all the working error variables
        double error = this.Setpoint - this.Input;
        this.errSum += (error * timeChange);
        double dErr = (error - this.lastErr) / timeChange;

        // Compute PID Output
        this.Output = this.kp * error + this.ki * this.errSum
                + this.kd * dErr;

        // Remember some variables for next time
        this.lastErr = error;
        this.lastTime = now;

        return this.Output;
    }

    void SetTunings(double Kp, double Ki, double Kd) {
        this.kp = Kp;
        this.ki = Ki;
        this.kd = Kd;
    }

    /** 
     * PID Controller Implementation for Accelerometer
     * 
     * Waweru and I have decided that the derivative part for the
     * controller is unnecessary. Derivative function taken out.
     * 
     * @author Joey
     */
    public double accelerometerPID(double Kp, double Ki){
    	// Time since last calculation
    	double now = getTime();
        double timeChange = now - lastTime;
        
        //Calculate error variables
        double error = Setpoint - Input;
        errSum += (error * timeChange);
        
        //Sum errors
        Output = kp * error + ki * errSum;
        
        //Reset time variable
        lastTime = now;
        
        return Output;
    }
    
    /**
     * Method called to set the Setpoint so the PID controller has the
     * capability to calculate errors and correct them.
     *
     * @param defSetpoint
     *            double value
     * @author Jack Rausch
     */
    public void setSetpoint(double defSetpoint) {
        this.Setpoint = defSetpoint;
    }

    /**
     * Timer method integrating the Timer class from wpilibj. USE THIS
     * TIMER UNIVERSALLY!!!!!
     *
     * @author Jack Rausch
     */
    public void startTimer() {
        this.timer.start();
    }

    /**
     * Called to retrieve the Time from previously defined method
     * "startTimer"
     *
     * @author Jack Rausch
     */
    public double getTime() {
        double currentTime = this.timer.get();
        return currentTime;
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

            static final double armMoveMaxTime = 2d;

            static final double armDown = -1, armUp = 1, armStop = 0;

            public static final double intakeDelay = 1d;
        }

        /**
         * Constant for autoLowBar.
         *
         * @author James
         */
        private static final class ConstLowBar {

            public static final double driveThroughDelay = 5d;
        }

        /**
         * Constant for autoSpyBotLowGoal.
         *
         * @author James
         */
        private static final class ConstSpyBotLowGoal {

            public static final double driveToDelay = 5d;
        }

        /**
         * Constant for autoChevalDeFrise.
         *
         * @author James
         */
        private static final class ConstChevalDeFrise {

            public static final double driveToDelay = 5d;
            public static final double driveThroughDelay = 5d;
        }

        /**
         * Constant for autoPortcullis.
         *
         * @author James
         */
        public static final class ConstPortcullis {

            public static final double driveDelay = 5d;
            public static final double driveThroughDelay = 5d;
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
     * to lower arm. Need more info.
     *
     * @author James
     */
    private void armLowerBottom() {
        this.arm.set(Constant.Shared.armDown);
        Autonomous.delay(Constant.Shared.armMoveMaxTime);
        this.arm.set(Constant.Shared.armStop);
    }

    /**
     * to delay for some time. Need more info.
     *
     * @author James
     * @param delayTime
     *            delay time in seconds
     */
    private static void delay(double delayTime) {
        // Just realized there is a delay thing in Timer. But I'd just
        // stick to using Autonomous.delay.
        edu.wpi.first.wpilibj.Timer.delay(delayTime);
    }

    /**
     * to lift arm. Need more info
     *
     * @author James
     */
    private void armLifterTop() {
        this.arm.set(Constant.Shared.armUp);
        Autonomous.delay(Constant.Shared.armMoveMaxTime);
        this.arm.set(Constant.Shared.armStop);
    }

    /**
     * To drive straight some distance.
     *
     * @author James
     * @param driveDistance
     *            Meters of driving
     * @param PLACEHOLDER
     *            This is just a placeholder and does not do anything.
     *            You can just use empty string "" for this.
     */
    private void driveStraight(double driveDistance,
            Object PLACEHOLDER) {
        PLACEHOLDER = "";
    }

    /**
     * Place Holder. To drive straight. Need more info.
     *
     * @author James
     * @param driveTime
     *            Seconds of driving
     */
    private void driveStraight(double driveTime) {
        // command starts
        this.dT.drive(Const.Motor.Run.Forward);
        Autonomous.delay(driveTime);
        this.dT.drive(Const.Motor.Run.Stop);
    }
    
    /**
     * throw ball out. Yet tested.
     *
     * @author James
     */
    private void throwBall() {
        this.intake.set(Const.Motor.Run.Forward);
        Autonomous.delay(Constant.Shared.intakeDelay);
        this.intake.set(Const.Motor.Run.Stop);
    }

    /**
     * Autonomous function No.1
     *
     * @author James
     */
    public void autoLowBar() {
        this.armLowerBottom();
        this.driveStraight(Constant.ConstLowBar.driveThroughDelay);
    }

    /**
     * Autonomous function No.2
     *
     * @author James
     */
    public void autoSpyBotLowGoal() {
        this.armLowerBottom();
        this.driveStraight(Constant.ConstSpyBotLowGoal.driveToDelay);
        this.throwBall();
    }

    /**
     * Autonomous function No.3
     *
     * @author James
     */
    public void autoChevalDeFrise() {
        this.driveStraight(Constant.ConstChevalDeFrise.driveToDelay);
        this.armLowerBottom();
        this.driveStraight(
                Constant.ConstChevalDeFrise.driveThroughDelay);
    }

    /**
     * Autonomous function No.4
     *
     * @author James
     */
    public void autoPortcullis() {
        this.armLowerBottom();
        this.driveStraight(Constant.ConstPortcullis.driveDelay);
        this.armLifterTop();
        this.driveStraight(
                Constant.ConstPortcullis.driveThroughDelay);
    }

    /**
     * Should be equivalent to a method called getAccel of another
     * class I2CAccelerometer which isn't here yet.
     *
     * @author James
     * @return Accelerations, double[] with length of 3
     */
    private static double[] I2CAccelerometer_getAccel() {
        double[] accel = new double[3];
        return accel; // placeholder
    }

    /**
     * Calculates distance traveled based on information from the
     * accelerometer.
     *
     * @author Joey
     * @return
     */
    private static void I2CDistanceTraveled() {

        Timer time = new Timer();

        time.start();
        for (int count = 0; count < (Autonomous.AUTOTIME
                * Autonomous.SAMPLINGRATE); count++) {
            Autonomous.delay(1 / Autonomous.SAMPLINGRATE);
            time.stop();
            double[] acceleration =
                    Autonomous.I2CAccelerometer_getAccel();

            Autonomous.position += .5 * acceleration[0] * time.get();
            time.reset();
            time.start();
        }
    }
    
    public static void pingerStart() {
        Runnable pinger = () -> {
            while (true)
                Autonomous.I2CAccelerometer_getAccel();
        };

        Autonomous.threadPing = new Thread(pinger);
        Autonomous.threadPing.start();

    }

    /**
     * Should be equivalent to a method called getAngles of another
     * class I2CGyro which isn't here yet.
     *
     * @author James
     * @return Angles, double[] with length of 3
     */
    private static double[] I2CGyro_getAngles() {
        double[] angles = new double[3];
        return angles; // placeholder
    }

}
