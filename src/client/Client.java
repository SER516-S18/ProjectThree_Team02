package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Run the client from this class
 *
 * @author Team 2, SER 516
 * @version 1.0 April 10, 2018
 */
public class Client extends Application {
    /**
     * Runs the client and adds client logic and data for the UI
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Client.fxml"));
        primaryStage.setTitle("Client");
        Scene scene = new Scene(root, 770, 509);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}