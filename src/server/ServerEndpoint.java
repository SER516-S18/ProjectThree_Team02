package server;

import javax.websocket.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@javax.websocket.server.ServerEndpoint(value = "/" + ServerNetworkService.FACE_ENDPOINT)
public class ServerEndpoint {

    protected static Set<Session> connections = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println(format("From Client(%s): %s", session.getId(), message));
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("%s connected", session.getId()));
        connections.add(session);
        throw new NullPointerException("demo");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(format("%s disconnected", session.getId()));
        connections.remove(session);
    }
}