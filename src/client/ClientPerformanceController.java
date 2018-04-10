package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Initializes Controller logic for Client
 * 
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
public class ClientPerformanceController implements Initializable {

    @FXML private AnchorPane parent;
    @FXML Chart graph3;
    @FXML Chart graph4;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML private void changeToExpressiveScene(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Client.fxml"));
        parent.getChildren().setAll(pane);
    }
}
