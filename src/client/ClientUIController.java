package client;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import model.EmotionalStatesData;
import model.EyeData;
import model.LowerFaceData;
import model.UpperFaceData;
import server.ServerUIModel;
import util.MyNumberStringConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientUIController extends ClientController implements Initializable {

	private ClientUIModel clientUIModel;
	@FXML	MenuBar initalMenu;
	@FXML	Menu clock;
	@FXML	Menu Detections;
	@FXML	Menu ConnectToServer;
	@FXML	AnchorPane rootPane;
	@FXML	MenuItem PerformanceItem;
	@FXML	MenuItem Connect;
	@FXML	Pane Graph1;
	@FXML	Pane Graph2;
	@FXML	Label Time;
	@FXML	Button SelectIp;
	@FXML	Button SelectPort;
	@FXML	Group headGroup;
	@FXML	Ellipse head;
	@FXML	Ellipse headOval;
	@FXML	SubScene headScene;

	private int FACE_PANE_HEIGHT = 400;
	private int FACE_PANE_WIDTH = 400;

	private int FACE_WIDTH = 160;
	private int FACE_HEIGHT = 200;

	private int HEAD_POSITION_X = FACE_PANE_WIDTH / 2;
	private int HEAD_POSITION_Y = FACE_PANE_HEIGHT / 2;

	private int EYES_POSITION_X = HEAD_POSITION_Y / 3 * 2;
	private int LEFT_EYE_POSITION = HEAD_POSITION_X / 3 * 2;
	private int RIGHT_EYE_POSITION = 2 * LEFT_EYE_POSITION;

	private int MOUTH_POSITION_X = HEAD_POSITION_X;
	private int MOUTH_POSITION_Y = HEAD_POSITION_Y / 3 * 4;

	private double START_X = FACE_WIDTH / 2 * 2.5;
	private double START_Y = FACE_HEIGHT / 3 * 2.5;
	private double LEFT_X = START_X - 30;
	private double RIGHT_X = START_X + 30;
	private double LEFT_Y = START_Y + 40;
	private double RIGHT_Y = LEFT_Y;

	private Ellipse rightEye;
	private Ellipse leftEye;
	private Arc mouth;
	private Arc leftEyebrow;
	private Arc rightEyebrow;
	private Group clenchedMouth;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long startTime = System.currentTimeMillis();
		ClientController cc = new ClientController();
		// ClientUIModel clientUIModel = new ClientUIModel();
		Group headGroup = initializeHead();
		rootPane.setBottomAnchor(headGroup, 30.00);
		rootPane.getChildren().add(headGroup);

		// MyNumberStringConverter converter = new MyNumberStringConverter();
		// Bindings.bindBidirectional(Time.textProperty(),
		// ClientUIModel.getInstance().timeElapsedProperty(), converter);
		addReceiveDataListner();
	}

	public void addReceiveDataListner() {
		ClientUIModel.getInstance().addReceviveDataListner(new ClientUIModel.ReceviveDataListner() {
			@Override
			public void onReceiveData(EmotionalStatesData emoStates, EyeData eyeData, LowerFaceData lowerFaceData,
					UpperFaceData upperFaceData) {
				updateTimeElapsed();
			}
		});

	}

	public void updateTimeElapsed() {
		Time.setText(String.valueOf(ClientUIModel.getInstance().getTimeElapsed()));
	}

	@FXML
	private void changeToPerformanceScene(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ClientPerformance.fxml"));
		rootPane.getChildren().setAll(pane);
	}

	@FXML
	private void PopUPMenuItem(ActionEvent event) throws IOException {
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
	 * @param event	Launch server event
	 */
	@FXML
	private void launchServer(ActionEvent event) {
		ClientController.getInstance().launchServer();
	}

	/**
	 * Draws head with all features in a neutral position
	 * 
	 * @return	head initialized as a group
	 */
	Group initializeHead() {
		Group headGroup = new Group();
		Ellipse head = drawHead();
		Polygon nose = setNose(START_X, START_Y, LEFT_X, LEFT_Y, RIGHT_X, RIGHT_Y);

		this.drawBothEyes();
		this.drawBothNeutralEyebrows();
		this.setMouthNeutral();
		this.setClenchedMouth(0);
		
		mouth.setVisible(true);
		clenchedMouth.setVisible(false);
		
		headGroup.getChildren().addAll(head, leftEye, rightEye, mouth, leftEyebrow, rightEyebrow, nose, clenchedMouth);

		return headGroup;
	}

	/**
	 * Draws an oval with shading for the head
	 * 
	 * @return	head with shading and coloring
	 */
	Ellipse drawHead() {
		Color FACE_COLOR = Color.rgb(232, 169, 85);
		Color LIGHTER_FACE_COLOR = Color.rgb(247, 231, 213);

		Ellipse headOval = new Ellipse(HEAD_POSITION_X, HEAD_POSITION_Y, FACE_WIDTH, FACE_HEIGHT);

		RadialGradient faceGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
				new Stop(0.0, LIGHTER_FACE_COLOR), new Stop(1.0, FACE_COLOR));

		headOval.setStroke(FACE_COLOR);
		headOval.setFill(faceGradient);

		headOval.setStrokeWidth(3);
		return headOval;
	}

	/**
	 * Draws both eyes facing forward
	 */
	public void drawBothEyes() {
		this.drawLeftEye();
		this.drawRightEye();
	}

	/**
	 * Draws right eye facing forward
	 */
	public void drawRightEye() {
		Ellipse ellipse = new Ellipse(RIGHT_EYE_POSITION, EYES_POSITION_X, 12.0f, 18.0f);
		rightEye = ellipse;
	}

	/**
	 * Draws left eye facing forward
	 */
	public void drawLeftEye() {
		Ellipse ellipse = new Ellipse(LEFT_EYE_POSITION, EYES_POSITION_X, 12.0f, 18.0f);
		leftEye = ellipse;
	}
	
	/**
	 * Draws both eyes facing forward, returned to neutral position
	 */
	public void lookStraight() {
		leftEye.setCenterX(LEFT_EYE_POSITION);
		leftEye.setRadiusX(12.0f);
		rightEye.setCenterX(RIGHT_EYE_POSITION);
		rightEye.setRadiusX(12.0f);
	}
	
	/**
	 * Draws both eyes looking to the left
	 */
	public void lookLeft() {
		leftEye.setCenterX(LEFT_EYE_POSITION - 20);
		leftEye.setRadiusX(6);
		rightEye.setCenterX(RIGHT_EYE_POSITION - 20);
		rightEye.setRadiusX(6);
	}
	
	/**
	 * Draws both eyes looking to the right
	 */
	public void lookRight() {
		leftEye.setCenterX(LEFT_EYE_POSITION + 20);
		leftEye.setRadiusX(6);
		rightEye.setCenterX(RIGHT_EYE_POSITION + 20);
		rightEye.setRadiusX(6);
	}
	
	/**
	 * Draws one eye appearing as if closed
	 * 
	 * @param eyeName	Eye that is currently closing
	 */
	public void closeEye(Ellipse eyeName) {
		this.lookStraight();
		eyeName.setRadiusY(1);
	}
	
	/**
	 * Draws one eye appearing as if opened
	 * 
	 * @param eyeName	Eye that is currently opening
	 */
	public void openEye(Ellipse eyeName) {
		this.lookStraight();
	}
	
	/**
	 * Closes both eyes simultaneously
	 */
	public void blinkEyes() {
		this.closeEye(leftEye);
		this.closeEye(rightEye);
		this.lookStraight(); //???? In a loop? Or a thread?
	}
	
	/**
	 * Initializes both eyes to a neutral position. Creates the arcs for both eyes.
	 */
	public void drawBothNeutralEyebrows() {
		Arc arc = new Arc(LEFT_EYE_POSITION, EYES_POSITION_X - 30, 35.0f, 2.0f, 0, 180.0f);
		arc.setType(ArcType.OPEN);
		arc.setStroke(Color.BLACK);
		arc.setFill(null);
		arc.setStrokeWidth(5);
		leftEyebrow = arc;
		
		Arc arc2 = new Arc(RIGHT_EYE_POSITION, EYES_POSITION_X - 30, 35.0f, 2.0f, 0, 180.0f);
		arc2.setType(ArcType.OPEN);
		arc2.setStroke(Color.BLACK);
		arc2.setFill(null);
		arc2.setStrokeWidth(5);
		rightEyebrow = arc2;
	}

	/**
	 * Draws both eyebrows raised
	 * 
	 * @param raisedEyebrowAmount	Value indicating how much eyebrows should be raised, received from server
	 */
	public void raiseBothEyebrows(double raisedEyebrowAmount) {
		// Reset eyebrow rotation to handle case of furrowing eyebrows first
		leftEyebrow.setRotate(0);
		rightEyebrow.setRotate(0);

		double raisedEyebrowHeight = (raisedEyebrowAmount * 100) - ((raisedEyebrowAmount * 100) / 2);
		rightEyebrow.setRadiusY(raisedEyebrowHeight);
		leftEyebrow.setRadiusY(raisedEyebrowHeight);
	}

	/**
	 * Draws both eyebrows furrowed
	 * 
	 * @param furrowedEyebrowValue	Value indicating how much eyebrows should be furrowed, received from server
	 */
	public void furrowBothEyebrows(double furrowedEyebrowValue) {
		// Reset eyebrow height to handle case of raising eyebrows first
		rightEyebrow.setRadiusY(2.0f);
		leftEyebrow.setRadiusY(2.0f);

		double furrowedLeftEyebrowAmount = (furrowedEyebrowValue * 10) + ((furrowedEyebrowValue * 10) / 2) + 15;
		double furrowedRightEyebrowAmount = -((furrowedEyebrowValue * 10) + ((furrowedEyebrowValue * 10) / 2) + 15);
		leftEyebrow.setRotate(furrowedLeftEyebrowAmount);
		rightEyebrow.setRotate(furrowedRightEyebrowAmount);
	}

	/**
	 * Creates and draws shape for nose
	 * 
	 * @param X1	X coordinate for point 1 of triangle for nose
	 * @param Y1	Y coordinate for point 1 of triangle for nose
	 * @param X2	X coordinate for point 2 of triangle for nose
	 * @param Y2	Y coordinate for point 2 of triangle for nose
	 * @param X3	X coordinate for point 3 of triangle for nose
	 * @param Y3	Y coordinate for point 3 of triangle for nose
	 * @return
	 */
	javafx.scene.shape.Polygon setNose(double X1, double Y1, double X2, double Y2, double X3, double Y3) {
		javafx.scene.shape.Polygon nose = new Polygon();
		nose.getPoints().addAll(new Double[] { X1, Y1, X2, Y2, X3, Y3 });
		Color nose_COLOR = Color.rgb(232, 169, 85);
		nose.setFill(nose_COLOR);
		return nose;
	}

	/**
	 * Initializes mouth to a neutral position
	 */
	public void setMouthNeutral() {
		Arc arc = new Arc(MOUTH_POSITION_X, MOUTH_POSITION_Y, 50.0f, 0, 180.0f, 180.0f);
		arc.setType(ArcType.OPEN);
		arc.setStroke(Color.BLACK);
		arc.setFill(null);
		arc.setStrokeWidth(3);
		mouth = arc;
	}

	/**
	 * Sets mouth to new smile amount
	 * 
	 * @param smileValue	Value received from Server, controls size of the smile
	 */
	public void setSmileAmount(double smileValue) {
		// Reset after other mouth expressions
		mouth.setRotate(0);
		mouth.setVisible(true);
		
		smileValue = smileValue * 100;
		mouth.setRadiusY(smileValue);
	}

	/**
	 * Sets mouth to new left smirk amount
	 * 
	 * @param smirkLeftValue	controls size of left smirk, received from server
	 */
	public void setLeftSmirk(double smirkLeftAmount) {
		// Reset after other mouth expressions
		mouth.setVisible(true);
		
		smirkLeftAmount = smirkLeftAmount * 10 * 3;
		mouth.setRadiusY(10.0f);
		mouth.setRotate(smirkLeftAmount);
	}

	/**
	 * Sets mouth to new right smirk amount
	 * 
	 * @param smirkRightValue	controls size of right smirk, received from server
	 */
	public void setRightSmirk(double smirkRightAmount) {
		// Reset after other mouth expressions
		mouth.setVisible(true);
		
		smirkRightAmount = -(smirkRightAmount * 10 * 3);
		mouth.setRadiusY(10.0f);
		mouth.setRotate(smirkRightAmount);
	}

	/**
	 * Sets mouth to new laugh amount
	 * 
	 * @param laughValue	controls amount of the laugh expression, received from server
	 */
	public void setLaughAmount(double laughValue) {
		// Reset after other mouth expressions
		mouth.setRotate(0);
		mouth.setVisible(true);
		
		laughValue = laughValue * 100;
		mouth.setFill(Color.BLACK);
		mouth.setRadiusY(laughValue);
	}

	/**
	 * Sets mouth to new clenched amount
	 * 
	 * @param clenchValue	controls amount of clench expression, received from server
	 */
	public void setClenchedMouth(double clenchValue) {
		double clenchAmount = clenchValue * 10 * 6;
		Rectangle clench = new Rectangle(MOUTH_POSITION_X - 75, MOUTH_POSITION_Y, 150, clenchAmount);
		clench.setStroke(Color.BLACK);
		clench.setFill(null);
		clench.setStrokeWidth(3);

		Rectangle clenchedTeeth = new Rectangle(MOUTH_POSITION_X - 75, MOUTH_POSITION_Y + 10, 150, 10);
		clenchedTeeth.setStroke(Color.BLACK);
		clenchedTeeth.setFill(null);
		clenchedTeeth.setStrokeWidth(3);

		clenchedMouth = new Group();
		clenchedMouth.getChildren().addAll(clench, clenchedTeeth);
		mouth.setVisible(false);
		clenchedMouth.setVisible(true);
	}
}
