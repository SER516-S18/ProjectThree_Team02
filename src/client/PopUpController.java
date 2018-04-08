package client;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML Spinner<SpinnerValueFactory.IntegerSpinnerValueFactory>
            addPortNumbers;
    @FXML Button connect;
    Scene scene;
    Stage Window;
    @FXML private AnchorPane parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This binding is needed for the free-edit portion of the port spinner
        // Ensures we always get the up-to-date value
        TextFormatter<SpinnerValueFactory.IntegerSpinnerValueFactory>
                formatter = new TextFormatter<>(
                    addPortNumbers.getValueFactory().getConverter(),
                    addPortNumbers.getValueFactory().getValue() );
        addPortNumbers.getEditor().setTextFormatter(formatter);
        addPortNumbers.getValueFactory().valueProperty()
                .bindBidirectional(formatter.valueProperty());
    }
    @FXML private void buttonClick(Event e)
    {
        String ip = ipAddress.getText();
        String port = addPortNumbers.getEditor().getText();
        System.out.println( "Attempting to connect to " + ip + ":" + port );
        ClientController.getInstance().connectToServer( ip, port );
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();

    }
}
