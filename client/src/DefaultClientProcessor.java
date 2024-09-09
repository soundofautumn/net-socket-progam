import java.util.Scanner;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 17:54
 */
public class DefaultClientProcessor implements ClientProcessor {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showUsage() {
        System.out.println("Usage:");
        System.out.println("login <username> <password>");
        System.out.println("register <username> <password>");
        System.out.println("echo <username> <message>");
    }

    private String processCommand(String commandStr) {
        final String[] commandStrs = commandStr.split(" ");
        if (commandStr.startsWith("login")) {
            if (commandStrs.length != 3) {
                System.out.println("Usage: login <username> <password>");
                return null;
            }
            return "login " + commandStrs[1] + " " + commandStrs[2];
        } else if (commandStr.startsWith("register")) {
            if (commandStrs.length != 3) {
                System.out.println("Usage: register <username> <password>");
                return null;
            }
            return "register " + commandStrs[1] + " " + commandStrs[2];
        } else if (commandStr.startsWith("echo")) {
            if (commandStrs.length < 3) {
                System.out.println("Usage: echo <username> <message>");
                return null;
            }
            return "echo " + commandStrs[1] + " " + commandStrs[2];
        }
        return null;
    }

    @Override
    public String send() {
        while (true) {
            System.out.print(">");
            final String commandStr = scanner.nextLine();
            final String result = processCommand(commandStr);
            if (result != null) {
                return result;
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
