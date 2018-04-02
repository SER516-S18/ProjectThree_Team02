package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientUIController implements Initializable {

    private ClientNetworkService<Void> networkThread;
    @FXML AnchorPane rootPane;
    @FXML private TextField systemStatus;
    @FXML private TextField systemUpTime;
    @FXML private ChoiceBox<String> headSet = new ChoiceBox<>();
    @FXML private TextField Status;
    @FXML private ChoiceBox<String> profile = new ChoiceBox<>();
    @FXML private TextField WirelessSignal;
    @FXML private TextField BatteryPower;
    @FXML private TextField inputName;
    @FXML Button AddName= new Button();
    @FXML Button SaveName;
    @FXML MenuItem loadSecondB ;
    @FXML MenuItem close;
    ObservableList<String> availableChoicesForHeadset = FXCollections.observableArrayList("0","1","2");
    ObservableList<String> availableChoicesForProfile = FXCollections.observableArrayList("jw","Sanay","Dough","Mellisa","Yuan");
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        networkThread = new ClientNetworkService<>();
        networkThread.restart();
        systemStatus.setText("Server running");
        systemUpTime.setText("40");
        headSet.setItems(availableChoicesForHeadset);
        headSet.getSelectionModel().selectFirst();
        profile.setItems(availableChoicesForProfile);
        profile.getSelectionModel().selectFirst();
        Status.setText("OK");
    }
    @FXML private void changeToAffectiveScene(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ClientAffective.fxml"));
        rootPane.getChildren().setAll(pane);

    }
   @FXML private  void closeApplication()
   {
       Platform.exit();
   }
   @FXML private void addName()
   {
     inputName.setVisible(true);

   }
   @FXML private void saveName()
   {
       String Name = inputName.getText();
       availableChoicesForProfile.add(Name);
       inputName.setText("");
       inputName.setVisible(false);
   }
}
