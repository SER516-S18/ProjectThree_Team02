package server;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.LogManager;

public class ServerController implements Initializable{

    public static final int PORT = 3000;
    public static final String HOST_NAME = "localhost";
    public static final String ROOT_PATH = "";
    public static final String FACE_ENDPOINT = "face";

    private Service<Void> networkThread;

    @FXML Button powerButton;
    @FXML TextArea logTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        networkThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask(){
                return new Task<Void>(){
                    @Override
                    protected Void call() throws Exception {

                        org.glassfish.tyrus.server.Server server =
                                new org.glassfish.tyrus.server.Server(
                                        HOST_NAME,
                                        PORT,
                                        "/" + ROOT_PATH,
                                        ServerEndpoint.class);
                        //Disable logger
                        LogManager.getLogManager().reset();
                        try {
                            server.start();
                            while(!isCancelled()){}
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            server.stop();
                        }
                        return null;
                    }
                };
            }
        };

        //Redirect stdout to text area
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };

        System.setOut(new PrintStream(out, true));

        System.out.print("Press Start Button to start server.\n");

        networkThread.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.print("Websocket Server Started: ws://" + HOST_NAME + ":" + PORT + "\n");
            }
        });

        networkThread.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.print("Error Occurred\n");
            }
        });

        networkThread.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println();
                System.out.print("Websocket Server Stopped\n");
            }
        });
    }

    @FXML private void powerControl(ActionEvent event){

        if(networkThread.isRunning()){
            networkThread.cancel();
            powerButton.setText("Start");
        }else{
            networkThread.restart();
            powerButton.setText("Stop");
        }

    }

    /**
     * Used to redirect stdout to textarea
     * It will run when UI thread idle
     * @param str
     * @see Platform
     */
    public void appendText(String str) {
        Platform.runLater(() -> logTextArea.appendText(str));
    }
}
