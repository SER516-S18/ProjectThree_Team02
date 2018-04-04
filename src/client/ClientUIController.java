package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientUIController implements Initializable {

    private ClientNetworkService<Void> networkThread;
    @FXML MenuBar initalMenu;
    @FXML Menu clock;
    @FXML Menu Detections;
    @FXML Menu ConnectToServer;
    @FXML AnchorPane rootPane;
    @FXML MenuItem PerformanceItem;
    @FXML Pane Face;
    @FXML Pane Graph1;
    @FXML Pane Graph2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkThread = new ClientNetworkService<>();
        networkThread.restart();

    }
    @FXML private void changeToPerformanceScene(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ClientPerformance.fxml"));
        rootPane.getChildren().setAll(pane);

    }
}