import java.util.List;
import java.util.Scanner;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 14:58
 */
public class Main {
    public static void main(String[] args) {
        ServerManager serverManager = new ServerManager();
        serverManager.run(args);
        serverManager.commandLoop();
        serverManager.stop();
    }
}