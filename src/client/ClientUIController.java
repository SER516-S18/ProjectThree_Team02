package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientUIController extends ClientController implements Initializable  {

    private ClientUIModel clientUIModel;
    @FXML MenuBar initalMenu;
    @FXML Menu clock;
    @FXML Menu Detections;
    @FXML Menu ConnectToServer;
    @FXML AnchorPane rootPane;
    @FXML MenuItem PerformanceItem;
    @FXML MenuItem Connect;
    @FXML Pane Graph1;
    @FXML Pane Graph2;
    @FXML Label Time;
    @FXML Button SelectIp;
    @FXML Button SelectPort;
    @FXML Group headGroup;
    @FXML Ellipse head;
    @FXML Ellipse headOval;
    @FXML SubScene headScene;
    int WINDOW_HEIGHT = 400;
    int WINDOW_WIDTH = 400;

    int FACE_WIDTH = 160;
    int FACE_HEIGHT = 200;

    int headPositionX = WINDOW_WIDTH/2;
    int headPositionY = WINDOW_HEIGHT/2;

    int eyesPositionY = headPositionY/3 *2;
    int leftEyePositionX =  headPositionX/3 *2;
    int rightEyePositionX = 2*leftEyePositionX;

    int mouthPositionX = headPositionX;
    int mouthPositionY = headPositionY/3 *4;

    double startX = FACE_WIDTH/2 * 2.5;
    double startY = FACE_HEIGHT/3*2.5;
    double leftX = startX-30;
    double rightX = startX+30;
    double leftY =startY+40;
    double rightY = leftY;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        long startTime = System.currentTimeMillis();
        ClientController cc = new ClientController();
        //ClientUIModel clientUIModel = new ClientUIModel();
        Group headGroup = setHead();
        rootPane.setBottomAnchor(headGroup, 30.00);
        rootPane.getChildren().add(headGroup);
        Platform.runLater(()->updateTimeElapsed());
        





    }
    
    public void updateTimeElapsed() {
    	clientUIModel = ClientUIModel.getInstance();
        System.out.println("updateTimeElapsed "+clientUIModel.getTimeElapsed());
        Time.setText(String.valueOf(clientUIModel.getTimeElapsed()));
    }
    
    @FXML private void changeToPerformanceScene(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ClientPerformance.fxml"));
        rootPane.getChildren().setAll(pane);


    }
    @FXML private  void  PopUPMenuItem(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ConnectPopUp.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Connect");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Handling for a launch new server menu option event
     *
     * @param event Launch server event
     */
    @FXML private void launchServer( ActionEvent event ){
        ClientController.getInstance().launchServer();
    }

    Group setHead() {

        Group headGroup = new Group();

        Ellipse head = drawHead();

        Ellipse leftEye = drawEye(leftEyePositionX,eyesPositionY);
        Ellipse rightEye = drawEye(rightEyePositionX,eyesPositionY);

        Arc leftEyeBrow = drawEyeBrow(leftEyePositionX, eyesPositionY-30);
        Arc rightEyeBrow = drawEyeBrow(rightEyePositionX, eyesPositionY-30);


        Arc mouth = setMouthNeutral();

        // Change value in increments of 0, 0.1, 0.2, ... 1 to test
        Arc smile = setSmileAmount(1, mouthPositionX, mouthPositionY);

        // Change value in increments of 0, 0.1, 0.2, ... 1 to test
        Arc laugh = setLaughAmount(1, mouthPositionX, mouthPositionY);

        // Change value in increments of 0, 0.1, 0.2, ... 1 to test
        Arc leftSmirk = setLeftSmirk(1, mouthPositionX, mouthPositionY);

        // Change value in increments of 0, 0.1, 0.2, ... 1 to test
        Arc rightSmirk = setRightSmirk(1, mouthPositionX, mouthPositionY);

        Polygon nose = setNose(startX, startY, leftX, leftY, rightX, rightY);

        Group clenchedMouth = new Group();

        // Change value in increments of 0, 0.1, 0.2, ... 1 to test
        Rectangle clench = setClenchAmount(0.6, mouthPositionX - 75, mouthPositionY);
        Rectangle clenchInside = insideClenchRectangle(mouthPositionX - 75, mouthPositionY + 10);
        clenchedMouth.getChildren().addAll(clench, clenchInside);

        headGroup.getChildren().addAll(head, leftEye,  rightEye, leftSmirk, leftEyeBrow, rightEyeBrow, nose);

        /**
         * Uncomment these to test different parts - Will connect with server data later
         */
        //headGroup.getChildren().addAll(head, leftEye,  rightEye, mouth);
        //headGroup.getChildren().addAll(head, leftEye,  rightEye, smile);
        //headGroup.getChildren().addAll(head, leftEye,  rightEye, laugh);
        //headGroup.getChildren().addAll(head, leftEye,  rightEye, leftSmirk);
        //headGroup.getChildren().addAll(head, leftEye,  rightEye, rightSmirk);

        return headGroup;
    }


    Ellipse drawHead() {
        Color FACE_COLOR = Color.rgb(232, 169, 85);
        Color LIGHTER_FACE_COLOR = Color.rgb(247, 231, 213);

        Ellipse headOval = new Ellipse(headPositionX, headPositionY, FACE_WIDTH, FACE_HEIGHT);

        RadialGradient faceGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, LIGHTER_FACE_COLOR), new Stop(1.0, FACE_COLOR));

        headOval.setStroke(FACE_COLOR);
        headOval.setFill(faceGradient);

        headOval.setStrokeWidth(3);
        return headOval;
    }

    /**
     *
     * @param X		Position of eyeball horizontally on head
     * @param Y		Position of eyeball vertically on head
     * @return		Eye drawing
     */
    Ellipse drawEye(int X, int Y) {
        Ellipse ellipse = new Ellipse(X, Y, 12.0f, 18.0f);
        return ellipse;
    }
    Arc drawEyeBrow(int X, int Y) {
        Arc arc = new Arc(X, Y, 35.0f,  5.0f, 0, 180.0f);
        arc.setType(ArcType.ROUND);
        arc.setStroke(Color.BLACK);
        arc.setFill(Color.BLACK);
        arc.setStrokeWidth(5);
        return arc;
    }
    javafx.scene.shape.Polygon setNose(double X1, double Y1, double X2, double Y2, double X3, double Y3) {
        javafx.scene.shape.Polygon nose = new Polygon();
        nose.getPoints().addAll(new Double[]{ X1, Y1, X2, Y2, X3, Y3});
        Color nose_COLOR = Color.rgb(232, 169, 85);
        nose.setFill(nose_COLOR);
        return nose;
    }

    /**
     * Initializes mouth to a neutral position
     * @return	Mouth in neutral position
     */
    Arc setMouthNeutral() {
        return setSmileAmount(0, mouthPositionX, mouthPositionY);
    }

    /**
     *
     * @param smileValue	Value received from Server, controls size of the smile
     * @param X			Position of the smile horizontally on the head
     * @param Y			Position of the smile vertically on the head
     * @return			Smile with updated size
     */
    Arc setSmileAmount(double smileValue, int X, int Y) {
        double smileHeight =  smileValue * 100;
        Arc arc = new Arc(X, Y, 50.0f, smileHeight, 180.0f, 180.0f);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLACK);
        arc.setFill(null);
        arc.setStrokeWidth(3);
        return arc;
    }

    /**
     *
     * @param smirkLeftValue	Value received from Server, controls size of the smirk
     * @param X					Position of the smirk horizontally on the head
     * @param Y					Position of the smirk vertically on the head
     * @return					Left smirk with updated size
     */
    Arc setLeftSmirk(double smirkLeftValue, int X, int Y) {
        double smirkLeftAmount = smirkLeftValue * 10 * 3;
        Arc arc = new Arc(X, Y, 50.0f, 10.0f, 180.0f, 180.0f);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLACK);
        arc.setFill(null);
        arc.setStrokeWidth(3);

        arc.setRotate(smirkLeftAmount);
        return arc;
    }

    /**
     *
     * @param smirkRightValue	Value received from Server, controls size of the smirk
     * @param X					Position of the smirk horizontally on the head
     * @param Y					Position of the smirk vertically on the head
     * @return					Right smirk with updated size
     */
    Arc setRightSmirk(double smirkRightValue, int X, int Y) {
        double smirkRightAmount = -(smirkRightValue * 10 * 3);
        Arc arc = new Arc(X, Y, 50.0f, 10.0f, 180.0f, 180.0f);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLACK);
        arc.setFill(null);
        arc.setStrokeWidth(3);

        arc.setRotate(smirkRightAmount);
        return arc;
    }

    /**
     *
     * @param laughValue	Value received from Server, controls amount of the laugh expression
     * @param X				Position of the laugh expression horizontally on the head
     * @param Y				Position of the laugh expression vertically on the head
     * @return				Laugh expression with updated size
     */
    Arc setLaughAmount(double laughValue, int X, int Y) {
        double laughHeight =  laughValue * 100;
        Arc arc = new Arc(X, Y, 50.0f, laughHeight, 180.0f, 180.0f);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLACK);
        arc.setFill(Color.BLACK);
        arc.setStrokeWidth(3);
        return arc;
    }

    /**
     *
     * @param clenchValue	Value received from Server, controls amount of the clench expression
     * @param X				Position of the clench expression horizontally on the head
     * @param Y				Position of the clench expression vertically on the head
     * @return				Clench expression with updated size
     */
    Rectangle setClenchAmount(double clenchValue, int X, int Y) {
        double clenchAmount = clenchValue * 10 * 6;
        Rectangle clench = new Rectangle(X, Y, 150, clenchAmount);
        clench.setStroke(Color.BLACK);
        clench.setFill(null);
        clench.setStrokeWidth(3);
        return clench;
    }

    /**
     *
     * @param X				Position of the clench expression horizontally on the head
     * @param Y				Position of the clench expression vertically on the head
     * @return				Clench expression with updated size to go inside the mouth expression
     */
    Rectangle insideClenchRectangle(int X, int Y) {
        Rectangle clench = new Rectangle(X, Y, 150, 10);
        clench.setStroke(Color.BLACK);
        clench.setFill(null);
        clench.setStrokeWidth(3);
        return clench;
    }

}


