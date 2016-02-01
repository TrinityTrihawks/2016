
package frc.team4215.stronghold;

import java.io.*;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

/**
 * I'm still trying to find out what can be done with
 * networking and what to do with it
 *
 * @author James
 */
public class RoboNet {

    ServerSocket serverSocket;
    Socket socket;
    boolean hasError;
    public static final int port = 4215;
    private static final int serverTimeOutMilSec = 30000;
    
    public RoboNet() {
        this.roboNetInit();
    }

    private void roboNetInit() {
        this.hasError = false;
        try {
            this.serverSocket =
                    new ServerSocket(RoboNet.port, 1);
            this.socket = this.serverSocket.accept();
            this.serverSocket.setSoTimeout(
                    RoboNet.serverTimeOutMilSec);
        } catch (Throwable e) {
            this.hasError = true;
        }
    }
    
    private static void sendData(Socket socket,
            byte[] data) {
            
    }
}
