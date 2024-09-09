import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 17:55
 */
public class DefaultServerProcessor implements ServerProcessor {

    private Broadcast broadcast;

    private static final Map<String, String> userMap = new ConcurrentHashMap<>();

    private static final Map<String, String> onlineClients = new ConcurrentHashMap<>();

    static {
        userMap.put("admin", "admin");
        userMap.put("user", "user");
    }

    @Override
    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    @Override
    public String process(String client, String message) {
        if (message.startsWith("login")) {
            String[] split = message.split(" ");
            if (split.length != 3) {
                return "login failed";
            }
            String username = split[1];
            String password = split[2];
            if (onlineClients.containsKey(username)) {
                return "already login";
            }
            if (userMap.containsKey(username) && userMap.get(username).equals(password)) {
                onlineClients.put(username, client);
                return "login success";
            } else {
                return "login failed";
            }
        } else if (message.startsWith("register")) {
            String[] split = message.split(" ");
            if (split.length != 3) {
                return "register failed";
            }
            String username = split[1];
            String password = split[2];
            if (userMap.containsKey(username)) {
                return "register failed";
            }
            userMap.put(username, password);
            return "register success";
        } else if (message.startsWith("echo")) {
            String[] split = message.split(" ");
            if (split.length != 3) {
                return "unknown command";
            }
            String username = split[1];
            String echo = split[2];
            if (onlineClients.get(username) == null) {
                return "please login first";
            }
            if (!onlineClients.get(username).equals(client)) {
                return "please login first";
            }
            broadcast.broadcast(echo, username);
            return echo;
        }
        return "unknown command";
    }

    @Override
    public Collection<String> getOnlineClients() {
        return onlineClients.keySet();
    }

    @Override
    public boolean kick(String client) {
        return onlineClients.remove(client) != null;
    }
}
