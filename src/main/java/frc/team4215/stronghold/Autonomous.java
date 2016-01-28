
package frc.team4215.stronghold;

import edu.wpi.first.wpilibj.Victor;

/**
 * The class for Autonomous.
 * 
 * @author Josh<br/>
 *         Joey<br/>
 *         Tony<br/>
 *         James
 */
public class Autonomous {
    private Victor frontLeft, frontRight, backLeft, backRight, armMotor, intake;
    
    public Autonomous(Victor frontLeft_,
            Victor frontRight_,
            Victor backLeft_,
            Victor backRight_,
            Victor armMotor_,
            Victor intake_) {
        this.frontLeft = frontLeft_;
        this.frontRight = frontRight_;
        this.backLeft = backLeft_;
        this.backRight = backRight_;
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
        public static final class ConstLowBar {
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
        }
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
        edu.wpi.first.wpilibj.Timer delayTimer;
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
    private void driveStraight() {
    
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
        this.driveStraight();
        this.armLifterTop();
        
    }
    
}
