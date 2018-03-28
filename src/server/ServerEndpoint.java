package server;

import network.JsonPayload;
import network.JsonPayloadDecoder;
import network.JsonPayloadEncoder;

import javax.websocket.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@javax.websocket.server.ServerEndpoint(value = "/" + ServerController.FACE_ENDPOINT, encoders = JsonPayloadEncoder.class, decoders = JsonPayloadDecoder.class)
public class ServerEndpoint {

    private static Set<Session> connections = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(JsonPayload message, Session session) throws IOException, EncodeException {
        // TODO - Add data checks - Example for getting data
        /*if ("quit".equalsIgnoreCase(message.getContent())) {
            session.close();
        }*/
        System.out.println(format("From Client(%s): %s", session.getId(), message.getContent()));
        session.getBasicRemote().sendObject( message );
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("%s connected", session.getId()));
        connections.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(format("%s disconnected", session.getId()));
        connections.remove(session);
    }
}