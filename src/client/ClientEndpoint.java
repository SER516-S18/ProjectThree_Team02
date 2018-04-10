package client;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import static java.lang.String.format;

/**
 * Establishes endpoint for server connection
 * 
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
@javax.websocket.ClientEndpoint()
public class ClientEndpoint {

    /**
     * Occurs when connection starts
     * 
     * @param session   ID Number for session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println( format(
                "Connection established. session id: %s",
                session.getId() ) );
    }

    /**
     * Handles message received from Server
     * 
     * @param message   Message received from server
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println( "Received From Server: " + message );
        ClientController.getInstance().decodeMessage( message );
    }
}
