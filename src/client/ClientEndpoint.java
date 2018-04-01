package client;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import static java.lang.String.format;

@javax.websocket.ClientEndpoint()
public class ClientEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println( format(
                "Connection established. session id: %s",
                session.getId() ) );
    }

    @OnMessage
    public void onMessage( String message ) {
        System.out.println( "Received From Server: " + message );
        ClientController.getInstance().decodeMessage( message );
    }
}
