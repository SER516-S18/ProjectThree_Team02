package client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.glassfish.tyrus.client.ClientManager;
import javax.websocket.Session;

import java.net.URI;
import java.util.logging.LogManager;

public class ClientNetworkService<T> extends Service<T> {
    public static final String PORT = "3000";
    public static final String SERVER_ENDPOINT = "face";
    public static final String SERVER =
            "ws://localhost:" + PORT + "/" + SERVER_ENDPOINT;
    private static final long POLLING_SLEEP_TIME = 10; // ms

    /**
     * Create network Service
     * It can run as background thread
     * Make sure not blocking main JavaFX UI thread.
     * @see Service
     */
    @Override
    protected Task createTask() {
        addEventHandler();
        return new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                ClientManager client = ClientManager.createClient();
                Session session = client.connectToServer(
                        ClientEndpoint.class,
                        new URI( SERVER ) );
                //Disable default websocket client logger
                LogManager.getLogManager().reset();

                while(!isCancelled()){}

                return null;
            }
        };
    }

    /**
     * Add event handlers for network thread
     * @see Service
     */
    private void addEventHandler(){
        setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.print("Websocket Started: " + SERVER);

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
                System.out.print("Websocket Stopped\n");
                System.out.println();
            }
        });
    }
}
