package server;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * TODO: Add comment
 */
public class Server extends Application {

    //UI scene
    private static Scene scene;

    //Model
    private static ServerModel serverModel;

    /**
     * @return Singleton server model instance
     */
    protected static ServerModel getServerModel(){
        if( serverModel == null ){
            serverModel = new ServerModel();
        }
        return serverModel;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * NOTE: This method is called on the JavaFX Application Thread.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("serverUI.fxml"));
        primaryStage.setTitle("SER516 Team02 Server");

        scene = new Scene(root, 420, 500);

        primaryStage.setScene(scene);

        // Instantiates server model if not done already.
        getServerModel();

        //Binding data values with model
        TextField frequencyTextFiled = (TextField) scene.lookup("#frequencyTextField");
        Bindings.bindBidirectional(frequencyTextFiled.textProperty(), serverModel.emoStateIntervalProperty(), new NumberStringConverter());

        TextField upperfaceTextField = (TextField) scene.lookup("#upperfaceTextField");
        Bindings.bindBidirectional(upperfaceTextField.textProperty(), serverModel.upperfaceDataValueProperty(), new NumberStringConverter());

        TextField lowerfaceTextField = (TextField) scene.lookup("#lowerfaceTextField");
        Bindings.bindBidirectional(lowerfaceTextField.textProperty(), serverModel.lowerfaceDataValueProperty(), new NumberStringConverter());

        CheckBox autoresetCheckbox = (CheckBox) scene.lookup("#autoresetCheckbox");
        Bindings.bindBidirectional(serverModel.autoRestProperty(), autoresetCheckbox.selectedProperty());


        //Set window non-resizable
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {

        //Launch JavaFX UI
        //Main Thread
        launch(args);
    }
}
