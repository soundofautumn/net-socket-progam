import java.util.Scanner;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 17:54
 */
public class DefaultClientProcessor implements ClientProcessor {

    private boolean isLogin = false;

    private final Scanner scanner = new Scanner(System.in);

    private String username;

    @Override
    public String send() {
        if (!isLogin) {
            System.out.println("Please input username:");
            String username = scanner.nextLine();
            this.username = username;
            System.out.println("Please input password:");
            String password = scanner.nextLine();
            return "login " + username + " " + password;
        }
        System.out.println("Please input message:");
        final String message = scanner.nextLine();
        if (message.equals("exit")) {
            return null;
        }
        return "echo " + username + " " + message;
    }

    @Override
    public void receive(String message) {
        if (message.equals("login success")) {
            isLogin = true;
        }
        if (message.equals("login failed")) {
            isLogin = false;
        }
        if (message.equals("please login first")) {
            isLogin = false;
        }
        if (!message.startsWith("unknown command")) {
            System.out.println(message);
        }
    }
}
