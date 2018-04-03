package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.layout.AnchorPane;
import javafx.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
