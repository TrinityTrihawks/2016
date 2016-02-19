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
    
    public static Timer time = new Timer();
    
    private static double accelerometerKp;
    
    private static double accelerometerKi;

    // private static double lastTime;

    private static double setpointGyro;
    private static double setpointAccel;
    private static double errSumGyro;
    private static double errSumAccel;
    
    private static double gyroKp, gyroKi;
    private static double lastTimeGyro;
    private static double lastTimeAccel;
    
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

    public Autonomous(DriveTrain dT_) throws RobotException {
        dT = dT_;
        arm = new Arm();
        intake = new Intake();
        winch = new Winch();
    }

    /*
     * Actually, I highly doubt if this would work or not. If this
     * won't work I know how to fix it. - James
     */
    public void chooseAuto(int num) {
        if (num == 1) choiceAuto = () -> autoLowBar();
        else if (num == 2) choiceAuto = () -> autoSpyBotLowGoal();
        else if (num == 3) choiceAuto = () -> autoChevalDeFrise();
        // else if (num == 4) choiceAuto = () -> autoPortcullis();
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
    public static double accelerometerPID(double input) {
        // Time since last calculation
        double now = time.get();
        double timeChange = now - lastTimeAccel;

        // Calculate error variables
        double error = setpointAccel - input;
        errSumAccel += error * timeChange;

        // Sum errors
        double accelerometerError = accelerometerKp * error
                + accelerometerKi * errSumAccel;
                
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

        /**
         * Constant for autoPortcullis.
         *
         * @author James
         */
        @SuppressWarnings("unused")
        @Deprecated
        public static final class Portcullis {
            
            // public static final double driveToDistance = 500d;
            // public static final double driveThroughDistance = 500d;
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
    private void driveStraight(double moveDistance) {
        setpointGyro = 0;
        // lasting time
        Timer newtime = new Timer();
        newtime.start();
        double inittime = newtime.get();
        double lasttime = 1d; // seconds of lasting
        
        double[] angles = I2CGyro_getAngles();
        while (newtime.get() < (inittime + lasttime)) {
            dT.drive(1, gyroPID(angles[2]));
        }
        
        // while (distanceTraveled < moveDistance) {
        // dT.drive(Const.Motor.Run.Forward);
        // I2CDistanceTraveled();
        // }

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
    
    private void winchInit() {
        winch.set(Const.Motor.Run.WinchInitSpeed);
        Timer.delay(Const.Motor.Run.WinchInitTime);
        winch.set(Const.Motor.Run.WinchStop);
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
     * Should be equivalent to a method called getAccel of another
     * class I2CAccelerometer which isn't here yet.
     *
     * @author James
     * @return Accelerations, double[] with length of 3
     */
    private static double[] I2CAccel_getAccel() {
        return I2CAccel.getAccel();
    }

    /**
     * Calculates distance traveled based on information from the
     * accelerometer. The distanceTraveled is measured in inches.
     *
     * @author Joey
     */
    private static void I2CDistanceTraveled() {

        double time1 = time.get();
        for (int count =
                0; count < (AUTOTIME * SAMPLINGRATE); count++) {

            Timer.delay(1 / SAMPLINGRATE);
            double time2 = time.get();
            double timeChange = time2 - time1;
            time1 = time.get();
            double[] acceleration = I2CAccel_getAccel();

            distanceTraveled +=
                    .5 * acceleration[0] * Math.pow(timeChange, 2);
        }
    }
    
    public static void pingerStart() {
        Runnable pinger = () -> {
            while (true)
                I2CDistanceTraveled();
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
     * @see main.java.frc.team4215.stronghold.I2CGyro.getAngles()
     */
    private static double[] I2CGyro_getAngles() {
        return I2CGyro.getAngles();
    }

}
