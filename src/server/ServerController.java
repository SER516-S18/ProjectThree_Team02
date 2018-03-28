package server;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

        System.out.println("Press Start Button to start server.\n");

        networkThread.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Websocket Server Started: ws://" + HOST_NAME + ":" + PORT + "\n");
            }
        });

        networkThread.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Error Occurred\n");
            }
        });

        networkThread.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Websocket Server Stopped\n");
            }
        });

        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));

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
     *
     * @param str
     */
    public void appendText(String str) {
        Platform.runLater(() -> logTextArea.appendText(str));
    }
}
