
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
            static final double lowerAndLifterLastTime = 10;
            
            static final double armDown = -1, armUp = 1, armStop = 0;
        }
        
        /**
         * Const for Auto_*, if any.
         * 
         * @author Josh
         */
        public static final class Const_LowBar {
        }
        
        /**
         * Const for Auto_*, if any.
         * 
         * @author Joey
         */
        private static final class Const_SpyBot_LowGoal {
        }
        
        /**
         * Const for Auto_*, if any.
         * 
         * @author Tony
         */
        private static final class Const_Cheval_de_Frise {
        }
        
        /**
         * Const for Auto_*, if any.
         * 
         * @author James
         */
        public static final class Const_Portcullis {
        }
    }
    
    /**
     * Yet to be tested. Shared Method: Arm Lowering
     * 
     * @author James
     */
    private void ArmLowerBottom() {
        this.armMotor.set(Constant.Shared.armDown);
        Autonomous.delay(Constant.Shared.lowerAndLifterLastTime);
        this.armMotor.set(Constant.Shared.armUp);
    }
    
    // Not sure how to delay
    private static void delay(double delayTime) {
    }
    
    /**
     * Yet to be tested. Shared Method for Arm Lifting
     * 
     * @author James
     */
    private void ArmLifterTop() {
        final double setValue = 1;
    }
    
    /**
     * Autonomous function No.1
     * 
     * @author Josh
     */
    public void Auto_LowBar() {
    }
    
    /**
     * Autonomous function No.2
     * 
     * @author Joey
     */
    public void Auto_SpyBot_LowGoal() {
    }
    
    /**
     * Autonomous function No.3
     * 
     * @author Tony
     */
    public void Auto_Cheval_de_Frise() {
    }
    
    /**
     * Autonomous function No.4
     * 
     * @author James
     */
    public void Auto_Portcullis() {
    }
    
}
