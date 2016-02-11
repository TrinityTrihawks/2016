package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;

/**
 * The class for Autonomous.
 *
 * @author James
 */
public class Autonomous {

    private Victor frontLeft, frontRight, backLeft, backRight,
            armMotor, intake;
    private Interface choiceAuto;

    public Autonomous(Victor frontLeft_, Victor frontRight_,
            Victor backLeft_, Victor backRight_, Victor armMotor_,
            Victor intake_) throws RobotException {
        this(new Victor[] { frontLeft_, frontRight_, backLeft_,
                backRight_, armMotor_, intake_ });
    }

    public Autonomous(Victor[] sixVictors) throws RobotException {
        if (sixVictors.length < 6) throw new RobotException(
                "Victor array's length is less than 6");
        // Point to the other constructor.
        Victor[] a = sixVictors; // a nickname
        this.frontLeft = a[0];
        this.frontRight = a[1];
        this.backLeft = a[2];
        this.backRight = a[3];
        this.armMotor = a[4];
        this.intake = a[5];
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
        /* How long since we last calculated */
        double now = Autonomous.getTime();
        double timeChange = now - this.lastTime;

        /* Compute all the working error variables */
        double error = this.Setpoint - this.Input;
        this.errSum += (error * timeChange);
        double dErr = (error - this.lastErr) / timeChange;

        /* Compute PID Output */
        this.Output = this.kp * error + this.ki * this.errSum
                + this.kd * dErr;

        /* Remember some variables for next time */
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
     * Method called to set the Setpoint so the PID controller has the
     * capability to calculate errors and correct them.
     *
     * @param defSetpoint
     *            double value
     * @author Jack Rausch
     */
    public static void setSetpoint(double defSetpoint) {
        double Setpoint = defSetpoint;
    }

    /**
     * Timer method integrating the Timer class from wpilibj. USE THIS
     * TIMER UNIVERSALLY!!!!!
     *
     * @author Jack Rausch
     */
    public static void startTimer() {
        javax.management.timer.Timer timer =
                new javax.management.timer.Timer();
        timer.start();
    }

    /**
     * Called to retrieve the Time from previously defined method
     * "startTimer"
     *
     * @author Jack Rausch
     */
    public static double getTime() {
        double currentTime = timer.get();
        return currentTime;
    }

    /**
     * All constants.
     *
     * @author James
     */
    private static final class Constant {

        /**
         * Const shared.
         *
         * @author James
         */
        public static final class Shared {

            static final double armMoveMaxTime = 2d;

            static final double armDown = -1, armUp = 1, armStop = 0;

            public static final double intakeDelay = 1d;
        }

        /**
         * Const for autoLowBar.
         *
         * @author James
         */
        private static final class ConstLowBar {

            public static final double driveThroughDelay = 5d;
        }

        /**
         * Const for autoSpyBotLowGoal.
         *
         * @author James
         */
        private static final class ConstSpyBotLowGoal {

            public static final double driveToDelay = 5d;
        }

        /**
         * Const for autoChevalDeFrise.
         *
         * @author James
         */
        private static final class ConstChevalDeFrise {

            public static final double driveToDelay = 5d;
            public static final double driveThroughDelay = 5d;
        }

        /**
         * Const for autoPortcullis.
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
        this.armMotor.set(Constant.Shared.armDown);
        Autonomous.delay(Constant.Shared.armMoveMaxTime);
        this.armMotor.set(Constant.Shared.armStop);
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
        this.armMotor.set(Constant.Shared.armUp);
        Autonomous.delay(Constant.Shared.armMoveMaxTime);
        this.armMotor.set(Constant.Shared.armStop);
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
        // getting the victor[] array.
        Victor[] vicList = new Victor[] { this.frontLeft,
                this.frontRight, this.backLeft, this.backRight };
        // command starts
        Autonomous.setVictorArray(vicList, Const.Motor.Run.Forward);
        DriveTrain dT = new DriveTrain(this.frontLeft, this.backLeft,
                this.frontRight, this.backRight);
        // command starts
        dT.drive(Const.Motor.Run.Forward);
        Autonomous.delay(driveTime);
        dT.drive(Const.Motor.Run.Stop);
    }

    private static void setVictorArray(Victor[] vicList,
            double setValue) {
        for (Victor v : vicList)
            v.set(setValue);
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
    private static double[] I2CDistanceTraveled() {
        while (true) {
            double[] acceleration =
                    Autonomous.I2CAccelerometer_getAccel();
            double[] vtx = acceleration[0] * dt;
            double[] vty = acceleration[1] * dt;
            double[] xt = vtx * dt;
            double[] yt = vty * dt;
        }
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
