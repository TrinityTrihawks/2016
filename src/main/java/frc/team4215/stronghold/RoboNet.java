
package frc.team4215.stronghold;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * I'm still trying to find out what can be done with networking and
 * what to do with it
 *
 * @author James
 */
public class RoboNet {
    
    private DatagramSocket datagramSocket;
    private DatagramPacket currentDP;
    private boolean hasError;

    public RoboNet() {
        this.roboNetInit();
    }
    
    private void roboNetInit() {
        this.hasError = false;
        try {
            this.datagramSocket =
                    new DatagramSocket(Const.Net.Num.PORT);
        } catch (Throwable e) {
            this.hasError = true;
        }
    }
    
    public static void sendData(String dataString)
            throws IOException, RobotException {
        new RoboNet().send(dataString);
    }
    
    private void send(String dataString)
            throws IOException, RobotException {
        if (this.hasError) throw new RobotException(
                "There is some unknown error before running this function.");
        byte[] buffer = dataString.getBytes();
        InetAddress receiver =
                InetAddress.getByName(Const.Net.Num.RASPBERRY);
        this.currentDP = new DatagramPacket(buffer, buffer.length,
                receiver, Const.Net.Num.PORT);
        this.datagramSocket.send(this.currentDP);

    }
}
