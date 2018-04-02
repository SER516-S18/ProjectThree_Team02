package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientAffectiveController implements Initializable {
    @FXML private AnchorPane parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML private void changeToExpressiveScene(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Client.fxml"));
        parent.getChildren().setAll(pane);

    }
}
