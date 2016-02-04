
package frc.team4215.stronghold;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Data sending. You only need to use the static function
 * {@link RoboNet#sendData(String)} to send data.
 *
 * @author James
 * @version 2016.3.5 <!--Yr.Wk.Dy-->
 */
public class RoboNet {
    
    private DatagramSocket datagramSocket;
    private DatagramPacket currentDP;
    private boolean hasError;

    private RoboNet() {
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

    /**
     * The public entrance of sending data to raspberrypi.
     *
     * @author James
     * @param dataString
     *            The String data.
     * @throws IOException
     *             Exception for InetAddress.getByName(String) and
     *             DatagramSocket.send(DatagramPacket).
     * @throws RobotException
     *             Exception occuring when unknown errors happen
     */
    public static void sendData(String dataString)
            throws IOException, RobotException {
        new RoboNet().send(dataString);
    }
    
    /**
     * The private entrance of sending data to raspberrypi.
     *
     * @param dataString
     *            The String data.
     * @throws IOException
     * @throws RobotException
     * @see RoboNet#sendData(String)
     */
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
