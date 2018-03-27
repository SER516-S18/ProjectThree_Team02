package client;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.net.URI;
import java.util.Scanner;

import static util.JsonUtil.formatMessage;

public class Client {

    public static final String PORT = "3000";
    public static final String SERVER_ENDPOINT = "face";
    public static final String SERVER =
            "ws://localhost:" + PORT + "/" + SERVER_ENDPOINT;

    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();
        String message;

        // Connect to server
        Scanner scanner = new Scanner(System.in);
        Session session = client.connectToServer(
                ClientEndpoint.class,
                new URI( SERVER ) );
        // repeatedly read a network.message and send it to the server
        // (until quit)
        do {
            message = scanner.nextLine();
            String msg = formatMessage( message );
            System.out.println( "Sending to server: " + msg );
            session.getBasicRemote().sendText( msg );
        } while (!message.equalsIgnoreCase("quit"));
    }

}
