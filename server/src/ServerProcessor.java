import java.util.Collection;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 16:17
 */
public interface ServerProcessor {

    String process(String client, String message);

    Collection<String> getOnlineClients();

    boolean kick(String client);
}
