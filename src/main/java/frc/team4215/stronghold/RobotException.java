
package frc.team4215.stronghold;

public class RobotException extends Exception {

    /**
     * computer genrated serialVersionUID.
     */
    private static final long serialVersionUID =
            -6564268929214879778L;

    public String exceptionString;

    public RobotException(String exceptionString_) {
        exceptionString = exceptionString_;
    }
    
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println(exceptionString);
    }
}
