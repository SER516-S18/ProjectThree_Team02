package server;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * TODO: Add comment
 */
public class ServerController implements Initializable{

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
        networkThread = new ServerNetworkService();

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
            //Setting button style class to unpressedPowerButton
            ObservableList<String> styleClasses = powerButton.getStyleClass();
            for(int i = 0; i < styleClasses.size(); i++){
                if(styleClasses.get(i).equals("pressedPowerButton")){
                    powerButton.getStyleClass().set(i, "unpressedPowerButton");
                }
            }
        }else{
            networkThread.restart();
            frequencyTextField.setDisable(true);
            powerButton.setText("Stop");
            //Setting button style class to pressedPowerButton
            ObservableList<String> styleClasses = powerButton.getStyleClass();
            for(int i = 0; i < styleClasses.size(); i++){
                if(styleClasses.get(i).equals("unpressedPowerButton")){
                    powerButton.getStyleClass().set(i, "pressedPowerButton");
                }
            }
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
                System.out.println("Log file has been saved to: " + dest.toString() + curTime + ".txt");
            } catch (Exception e){
                System.out.println("Cannot Save File " + dest.toString());
            }
        }
    }

    /**
     * Event Listener for Button saveLogButton
     * When button be clicked will produce an event
     * Button fx:id: saveLogButton
     * @param event
     */
    @FXML private void menuExit(ActionEvent event){
        Platform.exit();
    }

}
