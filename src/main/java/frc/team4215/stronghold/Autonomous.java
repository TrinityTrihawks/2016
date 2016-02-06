
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;

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
    
    /**
     * All constants.
     *
     * @author James
     */
    private static final class Constant {
        
        /**
         * Const for all Auto_*.
         *
         * @author James
         */
        public static final class Shared {
            
            // You must test these.
            static final double armMoveMaxTime = 2;
            // play around with this value
            
            static final double armDown = -1, armUp = 1, armStop = 0;
        }
        
        /**
         * Const for autoLowBar, if any.
         *
         * @author Josh
         */
        private static final class ConstLowBar {
        }
        
        /**
         * Const for autoSpyBotLowGoal, if any.
         *
         * @author Joey
         */
        private static final class ConstSpyBotLowGoal {
        }
        
        /**
         * Const for autoChevalDeFrise, if any.
         *
         * @author Tony
         */
        private static final class ConstChevalDeFrise {
        }
        
        /**
         * Const for autoPortcullis, if any.
         *
         * @author James
         */
        public static final class ConstPortcullis {
            
            public static final double driveDelay = 5d;
        }
    }

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
     * @param delayTime
     *            delay time in seconds
     */
    private static void delay(double delayTime) {
        // Just realized there is a delay thing in Timer. But I'd just
        // stick to
        // using Autonomous.delay.
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
     * to drive straight. Need more info.
     *
     * @author James
     */
    private void driveStraight(double driveTime) {
        // getting the victor[] array.
        Victor[] vicList = new Victor[] { this.frontLeft,
                this.frontRight, this.backLeft, this.backRight };
        // command starts
        Autonomous.setVictorArray(vicList, Const.Motor.Run.Forward);
        Autonomous.delay(driveTime);
    }

    private static void setVictorArray(Victor[] vicList,
            double setValue) {
        for (Victor v : vicList)
            v.set(setValue);
    }

    /**
     * Autonomous function No.1
     *
     * @author Josh
     */
    public void autoLowBar() {
    }

    /**
     * Autonomous function No.2
     *
     * @author Joey
     */
    public void autoSpyBotLowGoal() {
    }

    /**
     * Autonomous function No.3
     *
     * @author Tony
     */
    public void autoChevalDeFrise() {
    }

    /**
     * Autonomous function No.4
     *
     * @author James
     */
    public void autoPortcullis() {
        // 1. arm down
        // 2. drive to portcullis
        // 3. life portcullis (arm up)
        // 4. drive through
        this.armLowerBottom();
        this.driveStraight(Constant.ConstPortcullis.driveDelay);
        this.armLifterTop();
        // then I don't know. Someone tell me how this works on
        // thursday.

    }

}
