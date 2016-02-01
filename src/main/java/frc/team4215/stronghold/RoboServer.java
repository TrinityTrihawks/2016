package frc.team4215.stronghold;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RoboServer {
    
    public static final int PORT = 4215;
    private static RoboThread roboThread;

    public static void main(String[] args) {
    
    }

    private class RoboThread extends Thread {

        public DatagramSocket socket;

        public RoboThread() throws SocketException {
            this("RoboServer");
        }

        public RoboThread(String name)
                throws SocketException {
            super(name);
            this.socket =
                    new DatagramSocket(RoboServer.PORT);

        }

        @Override
        public void run() {
            try {
                do {
                    byte[] buf = new byte[0x100];
                    // receive request
                    DatagramPacket packet =
                            new DatagramPacket(buf,
                                    buf.length);
                    this.socket.receive(packet);
                } while (false);
            } catch (IOException e) {
            }
        }
        
    }
    
}
