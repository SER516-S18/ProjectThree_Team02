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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.LogManager;

/**
 * TODO: Add comment
 */
public class ServerController implements Initializable{

    public static final int PORT = 3000;
    public static final String HOST_NAME = "localhost";
    public static final String ROOT_PATH = "";
    public static final String FACE_ENDPOINT = "face";

    private Service<Void> networkThread;

    @FXML private AnchorPane anchorPane;
    @FXML private Button powerButton;
    @FXML private TextArea logTextArea;
    @FXML private TextField frequencyTextField;

    /**
     * All works after create controller
     * All initial works should in this method
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){

        //Create Network Service
        initNetworkService();

        //Redirect stdout to text area
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                Platform.runLater(() -> logTextArea.appendText(String.valueOf((char) b)));
            }
        };
        System.setOut(new PrintStream(out, true));

        //Inital work done
        System.out.print("Press Start Button to start server.\n");
    }

    /**
     * Event Listener for Button powerButton
     * When button be clicked will produce an event
     * Button fx:id: powerButton
     * @param event
     */
    @FXML private void powerControl(ActionEvent event){

        if(networkThread.isRunning()){
            networkThread.cancel();
            frequencyTextField.setDisable(false);
            powerButton.setText("Start");
        }else{
            networkThread.restart();
            frequencyTextField.setDisable(true);
            powerButton.setText("Stop");
        }
    }

    /**
     * Event Listener for Button clearButton
     * When button be clicked will produce an event
     * Button fx:id: clearButton
     * @param event
     */
    @FXML private void clearControl(ActionEvent event){
        logTextArea.clear();
        System.out.print("Log console has been cleared!\n");
    }

    /**
     * Event Listener for Button saveLogButton
     * When button be clicked will produce an event
     * Button fx:id: saveLogButton
     * @param event
     */
    @FXML private void saveLogControl(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Log File");

        //Getting primary stage of UI
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        //Getting and setting default dir
        File userDir = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(userDir);

        //Setting initial file type
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));

        //Setting default file name
        //Default file name is xxxx.log, xxx -> current time
        DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
        String curTime = dateFormat.format(new Date());
        fileChooser.setInitialFileName(curTime);

        //Open save dialog, and get filepath
        File dest = fileChooser.showSaveDialog(stage);

        if(dest != null){
            //Getting log content
            String logContent = logTextArea.getText();
            try{
                //Save log file to local storage
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest.toString()), "utf-8"));
                writer.write(logContent);
                writer.close();
            } catch (Exception e){
                System.out.println("Cannot Save File " + dest.toString());
            }
        }
    }

    /**
     * Create network Service
     * It can run as background thread
     * Make sure not blocking main JavaFX UI thread.
     * @see Service
     */
    private void initNetworkService(){
        //Create new background network thread using JavaFX Service
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
                        //Disable default websocket server logger
                        LogManager.getLogManager().reset();
                        try {
                            server.start();
                            //When Main JavaFX thread call cancel() method, isCancelled will become true.
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

        //Add Event handlers for network thread
        addEventHnadlerForNetworkThread();
    }

    /**
     * Add event handlers for network thread
     * @see Service
     */
    private void addEventHnadlerForNetworkThread(){
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
                System.out.print("Websocket Server Stopped\n");
                System.out.println();
            }
        });
    }
}
