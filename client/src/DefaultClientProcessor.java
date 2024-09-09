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
        System.out.println("1. login");
        System.out.println("2. register");
        System.out.println("3. echo");
        System.out.println("Please input your choice:");
        final int command = scanner.nextInt();
        scanner.nextLine();
        if (command == 1) {
            System.out.println("Please input username:");
            String username = scanner.nextLine();
            this.username = username;
            System.out.println("Please input password:");
            String password = scanner.nextLine();
            return "login " + username + " " + password;
        } else if (command == 2) {
            System.out.println("Please input username:");
            String username = scanner.nextLine();
            System.out.println("Please input password:");
            String password = scanner.nextLine();
            return "register " + username + " " + password;
        } else if (command == 3) {
            System.out.println("Please input message:");
            final String message = scanner.nextLine();
            if (message.equals("exit")) {
                return null;
            }
            return "echo " + username + " " + message;
        } else {
            return "unknown command";
        }

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
