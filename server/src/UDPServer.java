import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 20:06
 */
public class UDPServer extends AbstractServer {

    private DatagramSocket socket;

    @Override
    public boolean start() {
        try {
            socket = new DatagramSocket(getPort());
        } catch (SocketException e) {
            return false;
        }
        return super.start();
    }

    @Override
    public boolean stop() {
        if (!super.stop()) {
            return false;
        }
        socket.close();
        return true;
    }

    @Override
    public String getProtocol() {
        return "UDP";
    }

    void accept() {
        try {
            final byte[] receiveBuffer = new byte[1024];
            final DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            final String client = receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort();
            final String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(client + " -> " + receiveMsg);
            final String sendMsg = getProcessor().process(client, receiveMsg);
            System.out.println(client + " <- " + sendMsg);
            final byte[] sendBuffer = sendMsg.getBytes();
            final DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
            socket.send(sendPacket);
        } catch (IOException e) {
            if (!isRunning()) {
                return;
            }
            throw new RuntimeException(e);
        }

    }
}
