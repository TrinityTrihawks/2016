
package frc.team4215.stronghold;

import java.io.IOException;
import java.net.*;

/**
 * @author James
 */
public class RoboNet {

    /*
     * I'm still trying to find out what can be done with networking and what to
     * do
     */
    ServerSocket serverSocket;
    Socket socket;
    int errorCode;
    String errorMessage;
    public static final int port = 4215;
    
    public RoboNet() {
        // Error Mode init
        this.errorCode = 0;
        this.errorMessage = null;
        try {
            this.serverSocket = new ServerSocket();
            this.socket = new Socket();
        } catch (IOException e) {
            this.errorMessage = e.getMessage();
        }
    }
    
    public void connect() {
    
    }
}
