
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
    
    public Autonomous(Victor frontLeft_, Victor frontRight_, Victor backLeft_,
            Victor backRight_) {
        this.frontLeft = frontLeft_;
        this.frontRight = frontRight_;
        this.backLeft = backLeft_;
        this.backRight = backRight_;
    }
    
    private static final class Const {
        
        static final double setValue = -1;
        
        static final double lastTime = 10;
    }
    
    /**
     * Yet to be tested. Shared Method: Arm Lowering
     * 
     * @author James
     */
    private void ArmLowerButton() {
        
        final double setValue = -1; // unsure if this is right
        final double lastTime = 10; // You must test this.
        this.armMotor.set(setValue);
    }
    
    /**
     * Yet to be tested. Shared Method: Arm Lifting
     * 
     * @author James
     */
    private void ArmLifterTop() {
        
        final double setValue = 1;
    }
    
    /**
     * Const for Auto_*, if any.
     * 
     * @author Josh
     */
    private static final class Const_LowBar {
    }
    
    /**
     * Autonomous function No.1
     * 
     * @author Josh
     */
    public void Auto_LowBar() {
    
    }
    
    /**
     * Const for Auto_*, if any.
     * 
     * @author Joey
     */
    private static final class Const_SpyBot_LowGoal {
    }
    
    /**
     * Autonomous function No.2
     * 
     * @author Joey
     */
    public void Auto_SpyBot_LowGoal() {
    
    }
    
    /**
     * Const for Auto_*, if any.
     * 
     * @author Tony
     */
    private static final class Const_Cheval_de_Frise {
    
    }
    
    /**
     * Autonomous function No.3
     * 
     * @author Tony
     */
    public void Auto_Cheval_de_Frise() {
    
    }
    
    /**
     * Const for Auto_*, if any.
     * 
     * @author James Yu
     */
    public static final class Const_Portcullis {
    
    }
    
    /**
     * Autonomous function No.4
     * 
     * @author James Yu
     */
    public void Auto_Portcullis() {
    
    }
}
