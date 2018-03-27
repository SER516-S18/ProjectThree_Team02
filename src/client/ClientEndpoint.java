package client;

import network.JsonPayload;
import network.JsonPayloadDecoder;
import network.JsonPayloadEncoder;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.text.SimpleDateFormat;

import static java.lang.String.format;

@javax.websocket.ClientEndpoint(encoders = JsonPayloadEncoder.class, decoders = JsonPayloadDecoder.class)
public class ClientEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println( format(
                "Connection established. session id: %s",
                session.getId() ) );
    }

    @OnMessage
    public void onMessage( JsonPayload message ) {
        System.out.println( "Received From Server: " + message.getContent() );
    }
}
