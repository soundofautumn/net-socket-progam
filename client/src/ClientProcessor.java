/**
 * @author SoundOfAutumn
 * @date 2024/9/2 16:01
 */
public interface ClientProcessor {

    String send();

    void receive(String message);
}
