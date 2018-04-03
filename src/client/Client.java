package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Client.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 770, 509));
        primaryStage.show();
        primaryStage.setResizable(false);


    }

    public static void main(String[] args) throws Exception {
        launch(args);

    }


}
