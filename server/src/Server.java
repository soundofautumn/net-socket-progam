/**
 * @author SoundOfAutumn
 * @date 2024/9/2 16:17
 */
public interface Server {

    void setPort(int port);

    default void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-p")) {
                setPort(Integer.parseInt(args[++i]));
            }
        }
    }

    boolean start();

    boolean stop();

    void run(Processor processor);
}
