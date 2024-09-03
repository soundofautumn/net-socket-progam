import java.util.List;
import java.util.Scanner;

/**
 * @author SoundOfAutumn
 * @date 2024/9/2 14:58
 */
public class Main {
    public static void main(String[] args) {
        final List<Server> servers = List.of(new TCPServer(), new UDPServer());
        final ServerProcessor processor = new DefaultServerProcessor();
        for (final Server server : servers) {
            server.parseArgs(args);
            server.setProcessor(processor);
            if (!server.start()) {
                System.err.println("Failed to start server");
            }
        }
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            if (command.equals("online")) {
                System.out.println(processor.getOnlineClients());
            }
            if (command.startsWith("kick")) {
                final String[] split = command.split(" ");
                if (split.length == 2) {
                    if (processor.kick(split[1])) {
                        System.out.println("Client kicked");
                    } else {
                        System.err.println("Failed to kick client");
                    }
                }
            }
        }
        for (final Server server : servers) {
            if (!server.stop()) {
                System.err.println("Failed to stop server");
            }
        }
        System.out.println("Server stopped");
    }
}