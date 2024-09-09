/**
 * @author SoundOfAutumn
 * @date 2024/9/2 16:01
 */
public interface ClientProcessor {

    void showUsage();

    String send();

    void receive(String message);
}
