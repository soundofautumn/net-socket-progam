import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 17:55
 */
public class DefaultServerProcessor implements Processor {

    private static final Map<String, String> userMap;

    private static final List<String> onlineClients = new CopyOnWriteArrayList<>();

    static {
        userMap = Map.of(
                "admin", "admin",
                "user", "user"
        );
    }

    @Override
    public String process(String message) {
        if (message.startsWith("login")) {
            String[] split = message.split(" ");
            if (split.length != 3) {
                return "login failed";
            }
            String username = split[1];
            String password = split[2];
            if (onlineClients.contains(username)) {
                return "already login";
            }
            if (userMap.containsKey(username) && userMap.get(username).equals(password)) {
                onlineClients.add(username);
                return "login success";
            } else {
                return "login failed";
            }
        }
        if (message.startsWith("echo")) {
            String[] split = message.split(" ");
            if (split.length != 3) {
                return "unknown command";
            }
            String username = split[1];
            String echo = split[2];
            if (!onlineClients.contains(username)) {
                return "please login first";
            }
            return echo;
        }
        return "unknown command";
    }

    @Override
    public Collection<String> getOnlineClients() {
        return onlineClients;
    }

    @Override
    public boolean kick(String client) {
        return onlineClients.remove(client);
    }
}
