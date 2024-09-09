import java.util.Scanner;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 17:54
 */
public class DefaultClientProcessor implements ClientProcessor {

    private final Scanner scanner = new Scanner(System.in);

    private String username;

    @Override
    public void showUsage() {
        System.out.println("Usage:");
        System.out.println("login <username> <password>");
        System.out.println("register <username> <password>");
        System.out.println("echo <username> <message>");
    }

    @Override
    public String send() {
        while (true) {
            final String commandStr = scanner.nextLine();
            if (commandStr.equals("exit")) {
                return null;
            }
            final String[] commandStrs = commandStr.split(" ");
            if (commandStr.startsWith("login")) {
                if (commandStrs.length != 3) {
                    System.out.println("Usage: login <username> <password>");
                    continue;
                }
                return "login " + commandStrs[1] + " " + commandStrs[2];
            } else if (commandStr.startsWith("register")) {
                if (commandStrs.length != 3) {
                    System.out.println("Usage: register <username> <password>");
                    continue;
                }
                return "register " + commandStrs[1] + " " + commandStrs[2];
            } else if (commandStr.startsWith("echo")) {
                if (commandStrs.length < 3) {
                    System.out.println("Usage: echo <username> <message>");
                    continue;
                }
                return "echo " + commandStrs[1] + " " + commandStrs[2];
            }
        }


    }

    @Override
    public void receive(String message) {
        if (!message.startsWith("unknown command")) {
            System.out.println(message);
        }
    }
}
