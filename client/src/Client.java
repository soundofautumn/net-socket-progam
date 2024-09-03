/**
 * @author SoundOfAutumn
 * @date 2024/9/2 15:53
 */
public interface Client {

    boolean connect();

    boolean close();

    void run(Processor processor);

    void setAddress(String address);

    void setPort(int port);

    default void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i")) {
                setAddress(args[++i]);
            }
            if (args[i].equals("-p")) {
                setPort(Integer.parseInt(args[++i]));
            }
        }
    }
}
