/**
 * @author SoundOfAutumn
 * @date 2024/9/3 15:43
 */
public abstract class AbstractServer implements Server {

    private int port = 8080;

    private Processor processor;

    private volatile boolean isRunning = false;

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    Processor getProcessor() {
        return processor;
    }

    @Override
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    abstract void accept();

    @Override
    public boolean start() {
        new Thread(() -> {
            System.out.println(getProtocol() + " Server started on port: " + port);
            while (isRunning) {
                accept();
            }
        }).start();
        isRunning = true;
        return true;
    }

    @Override
    public boolean stop() {
        isRunning = false;
        System.out.println(getProtocol() + " Server stopped");
        return true;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
