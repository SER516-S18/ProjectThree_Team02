package client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class PopUpController implements Initializable {
    @FXML TextField ipAddress;
    @FXML Spinner<SpinnerValueFactory.IntegerSpinnerValueFactory>
            addPortNumbers;
    @FXML Button connect;
    Scene scene;
    Stage Window;
    @FXML private AnchorPane parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    /**
     * Establishes connection with Server through the button clicked
     * @param event
     */
    @FXML private void buttonClick(Event event)
    {
        String ip = ipAddress.getText();
        String port = addPortNumbers.getEditor().getText();
        System.out.println( "Attempting to connect to " + ip + ":" + port );
        ClientController.getInstance().connectToServer( ip, port );
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
