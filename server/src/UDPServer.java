import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 20:06
 */
public class UDPServer implements Server {

    private int port = 8080;

    private Processor processor;

    private volatile boolean isRunning = false;

    private DatagramSocket receiveSocket;

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean start() {
        isRunning = true;
        receiveSocket = null;
        System.out.println("UDP Server started on port: " + port);
        return true;
    }

    @Override
    public boolean stop() {
        isRunning = false;
        receiveSocket.close();
        System.out.println("UDP Server stopped.");
        return true;
    }

    private void accept() {
        try {
            final byte[] receiveBuffer = new byte[1024];
            final DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            receiveSocket = new DatagramSocket(port);
            receiveSocket.receive(receivePacket);
            final String client = receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort();
            final String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(client + " -> " + receiveMsg);
            final String sendMsg = processor.process(receiveMsg);
            System.out.println(client + " <- " + sendMsg);
            final byte[] sendBuffer = sendMsg.getBytes();
            final DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
            receiveSocket.send(sendPacket);
            receiveSocket.close();
        } catch (IOException e) {
            if (!isRunning) {
                return;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(Processor processor) {
        this.processor = processor;
        System.out.println("UDP Server is running...");
        while (isRunning) {
            accept();
        }
    }
}
