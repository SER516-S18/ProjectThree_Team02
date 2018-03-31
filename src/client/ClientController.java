package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TextField systemStatus;
    @FXML private TextField systemUpTime;
    @FXML private ChoiceBox<String> headSet = new ChoiceBox<>();
    @FXML private TextField Status;
    @FXML private ChoiceBox<String> profile = new ChoiceBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        systemStatus.setText("Server running");
        systemUpTime.setText("40");
        ObservableList<String> availableChoicesForHeadset = FXCollections.observableArrayList("0","1","2");
        headSet.setItems(availableChoicesForHeadset);
        headSet.getSelectionModel().selectFirst();
        ObservableList<String> availableChoicesForProfile = FXCollections.observableArrayList("jw","Sanay","Dough","Mellisa","Yuan");
        profile.setItems(availableChoicesForProfile);
        profile.getSelectionModel().selectFirst();
        Status.setText("OK");


    }
}
