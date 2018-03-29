package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.net.URI;
import java.util.Scanner;
import javafx.*;


import static util.JsonUtil.formatMessage;

public class Client extends Application {

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
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Users/sanaydevi/Downloads/ProjectThree_Team02-master/src/client/Client.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 770, 600));
        primaryStage.show();
    }
}
