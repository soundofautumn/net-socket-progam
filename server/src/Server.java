/**
 * @author SoundOfAutumn
 * @date 2024/9/2 16:17
 */
public interface Server {

    void setPort(int port);

    int getPort();

    default void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p")) {
                setPort(Integer.parseInt(args[++i]));
            }
        }
    }

    void setProcessor(ServerProcessor processor);

    void send(String message, String client);

    boolean start();

    boolean stop();

    String getProtocol();

    boolean isRunning();
}
