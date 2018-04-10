package client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.glassfish.tyrus.client.ClientManager;
import javax.websocket.Session;
import java.net.URI;
import java.util.logging.LogManager;

/**
 * Establishes service between Client and Network
 * 
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
public class ClientNetworkService<T> extends Service<T> {
    public static final String SERVER_ENDPOINT = "face";
    public static final String PROTOCOL = "ws://";
    private String url;
    private static final long POLLING_SLEEP_TIME = 10; // ms
    private Session session;

    ClientNetworkService(String ip, String port){
        url = PROTOCOL + ip + ":" + port + "/" + SERVER_ENDPOINT;
    }

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
                ClientUIModel.getInstance().setConnectionStatus(
                        ClientUIController.CONNECTION_STATUS_CONNECTING );
                session = client.connectToServer(
                        ClientEndpoint.class,
                        new URI( url ) );
                if( session.isOpen() ){
                    System.out.println("Connected to: " + url);
                    ClientUIModel.getInstance().setConnectionStatus(
                            ClientUIController.CONNECTION_STATUS_CONNECTED );
                }

                //Disable default websocket client logger
                LogManager.getLogManager().reset();

                while(!isCancelled()){
                    Thread.sleep( POLLING_SLEEP_TIME );
                }

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
                System.out.println("Connecting to " + url);
            }
        });

        setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Error! Cannot connect to server: " +
                        event.getSource());
                ClientUIController.showErrorDialog(
                        "Failed to connect to the server");
                ClientUIModel.getInstance().setConnectionStatus(
                        ClientUIController.CONNECTION_STATUS_DISCONNECTED );
            }
        });

        setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Websocket Stopped\n");
                ClientUIModel.getInstance().setConnectionStatus(
                        ClientUIController.CONNECTION_STATUS_DISCONNECTED );            }
        });
    }
}
