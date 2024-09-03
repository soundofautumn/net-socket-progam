import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 19:56
 */
public class UDPClient implements Client {

    private String address = "127.0.0.1";

    private int port = 8080;

    private volatile boolean isRunning = false;


    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean connect() {
        isRunning = true;
        return true;
    }

    @Override
    public boolean close() {
        isRunning = false;
        return true;
    }

    private void process(Processor processor) {
        final String sendMsg = processor.send();
        if (sendMsg == null) {
            isRunning = false;
            return;
        }
        try {
            final byte[] sendBuffer = sendMsg.getBytes();
            final DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(address), port);
            final DatagramSocket sendSocket = new DatagramSocket();
            sendSocket.send(sendPacket);
            final byte[] receiveBuffer = new byte[1024];
            final DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            sendSocket.receive(receivePacket);
            final String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            processor.receive(receiveMsg);
            sendSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(Processor processor) {
        while (isRunning) {
            process(processor);
        }
    }


}
