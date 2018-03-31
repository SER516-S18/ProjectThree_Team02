package server;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javax.websocket.Session;

import java.io.IOException;
import java.util.logging.LogManager;

public class ServerNetworkService<T> extends Service<T>{

    public static final int PORT = 3000;
    public static final String HOST_NAME = "localhost";
    public static final String ROOT_PATH = "/";
    public static final String FACE_ENDPOINT = "face";

    /**
     * Create network Service
     * It can run as background thread
     * Make sure not blocking main JavaFX UI thread.
     * @see Service
     */
    @Override
    protected Task createTask() {
        addEventHnadler();
        return new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                org.glassfish.tyrus.server.Server server =
                        new org.glassfish.tyrus.server.Server(
                                HOST_NAME,
                                PORT,
                                ROOT_PATH,
                                ServerEndpoint.class);
                //Disable default websocket server logger
                LogManager.getLogManager().reset();
                try {
                    server.start();
                    //When Main JavaFX thread call cancel() method, isCancelled will become true.
                    while(!isCancelled()){
                        // Send data to the clients when in a sending state
                        // TODO - do this without polling
                        while(ServerController.getInstance().isSendingData()){
                            sendPayloadToAllClients();
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    server.stop();
                }
                return null;
            }
        };
    }

    /**
     * Add event handlers for network thread
     * @see Service
     */
    private void addEventHnadler(){
        setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.print("Websocket Server Started: ws://" + HOST_NAME + ":" + PORT + "\n");
            }
        });

        setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.print("Error Occurred\n");
            }
        });

        setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.print("Websocket Server Stopped\n");
                System.out.println();
            }
        });
    }

    /**
     * Send the JSON payload containing face values, frequency and status
     */
    private void sendPayloadToAllClients(){
        String jsonPayload = ServerController.getInstance().formatMessage();
        for( Session connection : ServerEndpoint.connections ){
            try {
                connection.getBasicRemote().sendText( jsonPayload );
                System.out.println( "Sent data to client " +
                        connection.getId() );
            } catch( IOException e ){
                System.out.println( "Failed to send data to client: "
                        + connection.getId() );
                e.printStackTrace();
            }
        }
    }
}
