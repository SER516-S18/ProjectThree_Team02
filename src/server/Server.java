package server;

import javax.websocket.DeploymentException;
import java.util.Scanner;

public class Server {

    public static final int PORT = 3000;
    public static final String HOST_NAME = "localhost";
    public static final String ROOT_PATH = "";
    public static final String FACE_ENDPOINT = "face";

    public static void main(String[] args) {

        org.glassfish.tyrus.server.Server server =
                new org.glassfish.tyrus.server.Server(
                        HOST_NAME,
                        PORT,
                        "/" + ROOT_PATH,
                        ServerEndpoint.class );
        try {
            server.start();
            System.out.println( "Press any key to stop the server.." );
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
