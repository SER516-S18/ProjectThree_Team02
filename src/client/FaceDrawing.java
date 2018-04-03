package client;

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
import javafx.stage.Stage;


public class FaceDrawing extends Application {
    int WINDOW_HEIGHT = 400;
    int WINDOW_WIDTH = 400;

    int FACE_WIDTH = 180;
    int FACE_HEIGHT = 200;
    //   int SPACE_BETWEEN_EYES = 1 / 3 * FACE_WIDTH;


    // int EYE_HEIGHT = 1 / 8 * FACE_HEIGHT;
    // int EYE_WIDTH = 1 / 4 * FACE_WIDTH;



    int headPositionX = WINDOW_WIDTH/2;
    int headPositionY = WINDOW_HEIGHT/2;

    int eyesPositionY = headPositionY/3 *2;
    int leftEyePositionX =  headPositionX/3 *2;
    int rightEyePositionX = 2*leftEyePositionX;

    int mouthPositionX = headPositionX;
    int mouthPositionY = headPositionY/3 *4;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        Scene headScene = new Scene(setHead(), WINDOW_HEIGHT, WINDOW_WIDTH);

        primaryStage.setScene(headScene);
        primaryStage.show();
    }

    Group setHead() {

        Group headGroup = new Group();

        Ellipse head = drawHead();
        //Ellipse leftEye = drawEye(leftEyePositionX, EYE_POSITION_Y);

        // Ellipse eyeLeft = drawEye(leftEyePositionX,eyesPositionY);
        // Ellipse eyeRight = drawEye(rightEyePositionX,eyesPositionY);

        Circle leftEye = drawEyeBall(leftEyePositionX,eyesPositionY);
        Circle rightEye = drawEyeBall(rightEyePositionX,eyesPositionY);



        Arc mouth = mouth(mouthPositionX, mouthPositionY);

        headGroup.getChildren().addAll(head, leftEye,  rightEye, mouth);

        return headGroup;
    }


    Ellipse drawHead() {
        Color FACE_COLOR = Color.rgb(232, 169, 85);
        Color LIGHTER_FACE_COLOR = Color.rgb(247, 231, 213);

        Ellipse headOval = new Ellipse(headPositionX, headPositionY, FACE_WIDTH, FACE_HEIGHT);

        RadialGradient gradient6 = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, LIGHTER_FACE_COLOR), new Stop(1.0, FACE_COLOR));

        headOval.setStroke(FACE_COLOR);
        // headOval.setFill(Color.rgb(235, 193, 148, 1.0));
        headOval.setFill(gradient6);

        headOval.setStrokeWidth(3);
        return headOval;

    }

    Ellipse drawEye(int X, int Y) {
        Ellipse ellipse = new Ellipse(X, Y, 20.0f, 7.0f);
        ellipse.setStroke(Color.BLACK);
        ellipse.setFill(Color.WHITE);
        ellipse.setStrokeWidth(1);
        return ellipse;

    }
    Circle drawEyeBall(int X, int Y) {
        Circle circle = new Circle(X, Y, 10.0f);
        return circle;
    }
    Arc mouth(int X, int Y) {
        Arc arc = new Arc(X, Y, 50.0f, 50.0f, 180.0f, 180.0f);
        arc.setType(ArcType.ROUND);
        return arc;
    }

}

