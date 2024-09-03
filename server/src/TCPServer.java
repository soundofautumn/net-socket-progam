import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 16:16
 */
public class TCPServer implements Server {

    private int port = 8080;

    private ServerSocket serverSocket;

    private Processor processor;

    private ThreadPoolExecutor executor;

    private volatile boolean isRunning = false;

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            return false;
        }
        executor = new ThreadPoolExecutor(
                10,
                10,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                r -> new Thread(r, "TCP-Server-Thread"),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        System.out.println("TCP Server started on port: " + port);
        isRunning = true;
        return true;
    }

    @Override
    public boolean stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            return false;
        }
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            return false;
        }
        executor.shutdown();
        boolean isTerminated;
        try {
            isTerminated = executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!isTerminated) {
            System.err.println("Failed to stop server");
            return false;
        }
        System.out.println("TCP Server stopped.");
        serverSocket = null;
        executor = null;
        return true;
    }

    private void accept() {
        try {
            final Socket socket = serverSocket.accept();
            final String client = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            System.out.println("TCP Client connected: " + client);
            executor.execute(() -> {
                try {
                    final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    while (isRunning) {
                        final String receiveMsg = in.readLine();
                        if (receiveMsg == null) {
                            break;
                        }
                        System.out.println(client + " -> " + receiveMsg);
                        final String sendMsg = processor.process(receiveMsg);
                        System.out.println(client + " <- " + sendMsg);
                        out.write(sendMsg);
                        out.newLine();
                        out.flush();
                    }
                    socket.close();
                    System.out.println("TCP Client disconnected: " + client);
                } catch (IOException e) {
                    if (!isRunning) {
                        return;
                    }
                    throw new RuntimeException(e);
                }
            });
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
        System.out.println("TCP Server is running...");
        while (isRunning) {
            accept();
        }
    }
}
