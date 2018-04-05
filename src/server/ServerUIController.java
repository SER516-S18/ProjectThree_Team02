package server;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.EmotionalStatesData;
import model.EyeData;
import model.LowerFaceData;
import model.UpperFaceData;
import util.MyNumberStringConverter;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * TODO: Add comment
 */
public class ServerUIController implements Initializable{

    public static final String START_BUTTON_TEXT = "Start";
    public static final String STOP_BUTTON_TEXT = "Stop";

    private ServerNetworkService<Void> networkThread;

    @FXML private AnchorPane anchorPane;
    @FXML private Button powerButton;
    @FXML private TextArea logTextArea;
    @FXML private ChoiceBox choiceboxUpperface;
    @FXML private ChoiceBox choiceboxLowerface;
    @FXML private ChoiceBox choiceboxEye;
    @FXML private ChoiceBox emotionalStateChoiceBox;
    @FXML private CheckBox autoRepeatCheckbox;
    @FXML private TextField frequencyTextField;
    @FXML private TextField upperfaceTextField;
    @FXML private TextField lowerfaceTextField;
    @FXML private CheckBox autoresetCheckbox;

    /**
     * All works after create controller
     * All initial works should in this method
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){

        //Create and start network service
        networkThread = new ServerNetworkService();
        networkThread.restart();

        //Redirect stdout to text area
        //Using Platform.runLater() to aboid blocking main UI thread
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                Platform.runLater(() -> logTextArea.appendText(String.valueOf((char) b)));
            }
        };
        System.setOut(new PrintStream(out, true));

        //Binding data values with model
        bindModelWithUI();

        //Inital work done
    }


    private void bindModelWithUI() {
        MyNumberStringConverter converter = new MyNumberStringConverter();
        Bindings.bindBidirectional(frequencyTextField.textProperty(), ServerUIModel.getInstance().emoStateIntervalProperty(), converter);
        Bindings.bindBidirectional(upperfaceTextField.textProperty(), ServerUIModel.getInstance().upperfaceDataValueProperty(), converter);

        Bindings.bindBidirectional(lowerfaceTextField.textProperty(), ServerUIModel.getInstance().lowerfaceDataValueProperty(), converter);
        Bindings.bindBidirectional(ServerUIModel.getInstance().autoRestProperty(), autoresetCheckbox.selectedProperty());

        //Setting choice boxes
        choiceboxUpperface.setItems(FXCollections.observableArrayList(UpperFaceData.RAISE_BROW, UpperFaceData.FURROW_BROW));
        choiceboxUpperface.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                ServerUIModel.getInstance().setUpperfaceDataType(newValue);
            }
        });
        choiceboxUpperface.getSelectionModel().selectFirst();


        choiceboxLowerface.setItems(FXCollections.observableArrayList(LowerFaceData.SMILE, LowerFaceData.CLENCH,
                LowerFaceData.LAUGH, LowerFaceData.SMILE, LowerFaceData.SMIRK_LEFT, LowerFaceData.SMIRK_RIGHT));
        choiceboxLowerface.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                ServerUIModel.getInstance().setLowerfaceDataType(newValue);
            }
        });
        choiceboxLowerface.getSelectionModel().selectFirst();


        choiceboxEye.setItems(FXCollections.observableArrayList(EyeData.BLINK, EyeData.LOOK_LEFT, EyeData.LOOK_RIGHT,
                EyeData.WINK_LEFT, EyeData.WINK_RIGHT));
        choiceboxEye.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                ServerUIModel.getInstance().setEyeDataType(newValue);
            }
        });
        choiceboxEye.getSelectionModel().selectFirst();


        emotionalStateChoiceBox.setItems(FXCollections.observableArrayList(EmotionalStatesData.INTEREST,
                EmotionalStatesData.ENGAGEMENT, EmotionalStatesData.EXCITEMENT, EmotionalStatesData.FOCUS,
                EmotionalStatesData.RELAXATION, EmotionalStatesData.STRESS));
        emotionalStateChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                ServerUIModel.getInstance().setEmotionalStatesDataType(newValue);
            }
        });
        emotionalStateChoiceBox.getSelectionModel().selectFirst();

        //Setting auto-repeat default checked
        autoRepeatCheckbox.setSelected(true);
    }

    /**
     * Event Listener for Button powerButton
     * When button be clicked will produce an event
     * Button fx:id: powerButton
     * @param event
     */
    @FXML private void powerControl(ActionEvent event){
        if( networkThread.isRunning() ) {
            if (ServerUIModel.getInstance().isSendingData()) {
                ServerUIModel.getInstance().setSendingData(false);
                showStartButton();
                System.out.println("Stop sending data.");
            } else if (autoRepeatCheckbox.isSelected()) {
                double freq = ServerUIModel.getInstance().getEmoStateInterval();
                if (freq >= 0.0) {
                    ServerUIModel.getInstance().setSendingData(true);
                    showStopButton();
                    System.out.println("Sending Data, Auto repeat: open.");
                } else {
                    System.out.println("Invalid frequency value");
                }
            } else if (!ServerUIModel.getInstance().isSendingData()) {
                // If auto-repeat is not checked, then we want to send data one time
                // on a button click
                ServerUIModel.getInstance().setSendOnce(true);
            }
        } else {
            System.out.println("Network problem detected");
            showStartButton();
            ServerUIModel.getInstance().setSendingData(false);
            ServerUIModel.getInstance().setSendOnce(false);
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
        //System.out.print("Log console has been cleared!\n");
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
                //TODO: that maybe block Main UI thread, should find a way to optimize it
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

    private void showStopButton(){
        powerButton.setText(STOP_BUTTON_TEXT);
        //Setting button style class to pressedPowerButton
        ObservableList<String> styleClasses = powerButton.getStyleClass();
        for (int i = 0; i < styleClasses.size(); i++) {
            if (styleClasses.get(i).equals("unpressedPowerButton")) {
                powerButton.getStyleClass().set(i, "pressedPowerButton");
            }
        }
    }

    private void showStartButton(){
        powerButton.setText(START_BUTTON_TEXT);
        //Setting button style class to unpressedPowerButton
        ObservableList<String> styleClasses = powerButton.getStyleClass();
        for (int i = 0; i < styleClasses.size(); i++) {
            if (styleClasses.get(i).equals("pressedPowerButton")) {
                powerButton.getStyleClass().set(i, "unpressedPowerButton");
            }
        }
    }

    private boolean isDouble(String dblStr){
        boolean canParse = true;
        try{
            Double.valueOf(dblStr);
        } catch( NumberFormatException e ) {
            canParse = false;
        }
        return canParse;
    }
}
