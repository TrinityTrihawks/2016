package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Timer;

/**
 * The class for Autonomous.
 *
 * @author James
 */
public class Autonomous {
    
    private static Thread threadPing;
    private static double distanceTraveled;
    public static Timer time = new Timer();
    
    private static double accelerometerKp;
    private static double accelerometerKi;

    // private static double lastTime;
    private static double input;
    private static double setpoint;
    private static double errSum;
    private static double lastTime;

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

    private Interface choiceAuto;

    public Autonomous(DriveTrain dT_) throws RobotException {
        dT = dT_;
        arm = new Arm();
        intake = new Intake();
    }

    /*
     * Actually, I highly doubt if this would work or not. If this
     * won't work I know how to fix it. - James
     */
    public void chooseAuto(int num) {
        if (num == 1) choiceAuto = () -> autoLowBar();
        else if (num == 2) choiceAuto = () -> autoSpyBotLowGoal();
        else if (num == 3) choiceAuto = () -> autoChevalDeFrise();
        else if (num == 4) choiceAuto = () -> autoPortcullis();
        else choiceAuto = null;
    }

    public void autoChoice() throws RobotException {
        if (null != choiceAuto)
            throw new RobotException("There is not a method chosen.");
        choiceAuto.runAuto();
    }

    /* working variables */
    /*
     * private static double lastTime; private static double
     * Input,Output; private static double Setpoint; private static
     * double errSum,lastErr;
     */
    // private double kp, ki, kd;

    /*
     * Example PID controller
     * @author Jack Rausch
     */
    /*
     * public double errorCompute() { // How long since we last
     * calculated double now = time.get(); double timeChange = now -
     * lastTime; // Compute all the working error variables double
     * error = Setpoint - Input; errSum += (error * timeChange);
     * double dErr = (error - lastErr) / timeChange; // Compute PID
     * Output Output = kp * error + ki * errSum + kd * dErr; //
     * Remember some variables for next time lastErr = error; lastTime
     * = now; return Output; } void SetTunings(double Kp, double Ki,
     * double Kd) { kp = Kp; ki = Ki; kd = Kd; } // DO NOT DELETE THIS
     * CODE EVER!!!!!
     */

    /**
     * PID controller implementation for accelerometer Waweru and I
     * have decided that the derivative part for the controller is
     * unnecessary. Derivative function taken out. I moved this code
     * directly into the I2CDistanceTraveled section.
     *
     * @author Joey
     */
    public static double accelerometerPID(double accelerometerKp,
            double accelerometerKi) {
        // Time since last calculation
        double now = time.get();
        double timeChange = now - lastTime;
        
        // Calculate error variables
        double error = setpoint - input;
        errSum += error * timeChange;
        
        // Sum errors
        double accelerometerError =
                accelerometerKp * error + accelerometerKi * errSum;

        // Reset time variable
        lastTime = now;
        
        return accelerometerError;
    }
    
    /**
     * PID controller implementation for gyroscope
     *
     * @param gyroKp
     * @param gyroKi
     * @author Joey
     */
    public double gyroPID(double gyroKp, double gyroKi) {
        // Time since last calculation
        double now = time.get();
        double timeChange = now - lastTime;
        
        // Calculate error variables
        double error = setpoint - input;
        errSum += (error * timeChange);
        
        // Sum errors
        double gyroOutput = gyroKp * error + gyroKi * errSum;
        
        // Reset time variable
        lastTime = now;
        
        return gyroOutput;
    }
    
    /**
     * Method called to set the Setpoint so the PID controller has the
     * capability to calculate errors and correct them.
     *
     * @param defSetpoint
     *            double value
     * @author Jack Rausch
     */
    public static void setSetpoint(double defSetpoint) {
        setpoint = defSetpoint;
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
        arm.set(Constant.Shared.armDown);
        Timer.delay(Constant.Shared.armMoveMaxTime);
        arm.set(Constant.Shared.armStop);
    }

    /**
     * to lift arm. Need more info
     *
     * @author James
     */
    private void armLifterTop() {
        arm.set(Constant.Shared.armUp);
        Timer.delay(Constant.Shared.armMoveMaxTime);
        arm.set(Constant.Shared.armStop);
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
        final double moveDistance = 1000;
        while (distanceTraveled < moveDistance) {
            dT.drive(Const.Motor.Run.Forward);
        }

        dT.drive(Const.Motor.Run.Stop);
    }
    
    /**
     * throw ball out. Yet tested.
     *
     * @author James
     */
    private void throwBall() {
        intake.set(Const.Motor.Run.Forward);
        Timer.delay(Constant.Shared.intakeDelay);
        intake.set(Const.Motor.Run.Stop);
    }

    /**
     * Autonomous function No.1
     *
     * @author James
     */
    public void autoLowBar() {
        armLowerBottom();
        driveStraight(Constant.ConstLowBar.driveThroughDelay);
    }

    /**
     * Autonomous function No.2
     *
     * @author James
     */
    public void autoSpyBotLowGoal() {
        armLowerBottom();
        driveStraight(Constant.ConstSpyBotLowGoal.driveToDelay);
        throwBall();
    }

    /**
     * Autonomous function No.3
     *
     * @author James
     */
    public void autoChevalDeFrise() {
        driveStraight(Constant.ConstChevalDeFrise.driveToDelay);
        armLowerBottom();
        driveStraight(Constant.ConstChevalDeFrise.driveThroughDelay);
    }

    /**
     * Autonomous function No.4
     *
     * @author James
     */
    public void autoPortcullis() {
        armLowerBottom();
        driveStraight(Constant.ConstPortcullis.driveDelay);
        armLifterTop();
        driveStraight(Constant.ConstPortcullis.driveThroughDelay);
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

        double time1 = time.get();
        for (int count =
                0; count < (AUTOTIME * SAMPLINGRATE); count++) {

            Timer.delay(1 / SAMPLINGRATE);
            double time2 = time.get();
            double timeChange = time2 - time1;
            time1 = time.get();
            double[] acceleration = I2CAccelerometer_getAccel();

            distanceTraveled += .5 * acceleration[0]
                    * ((timeChange) * (timeChange)); // Measured in
                                                     // inches
        }
    }
    
    public static void pingerStart() {
        Runnable pinger = () -> {
            while (true)
                I2CAccelerometer_getAccel();
        };

        threadPing = new Thread(pinger);
        threadPing.start();

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
