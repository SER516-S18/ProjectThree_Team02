package client;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.sun.javafx.application.PlatformImpl.exit;

public class PopUpController implements Initializable {
    @FXML TextField ipAddress;
    @FXML Spinner addPortNumbers;
    @FXML Button Done;
    Scene scene;
    Stage Window;
    @FXML private AnchorPane parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    @FXML private  void buttonClick(Event e)
    {
        String ip = ipAddress.getText();
        String port = String.valueOf(
                (int) addPortNumbers.getValueFactory().getValue() );
        System.out.println( "Attempting to connect to " + ip + ":" + port );
        ClientController.getInstance().connectToServer( ip, port );
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();

    }
}
