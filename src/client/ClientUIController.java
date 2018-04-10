package client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.EmotionalStatesData;
import model.EyeData;
import model.LowerFaceData;
import model.UpperFaceData;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Creates pane for client class.
 * Contains logic for face. Adds face to pane.
 * Contains logic for facial expression graphs. Adds graphs to pane.
 * Contains logic for connection status indicator. Adds indicator status to pane.
 * 
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
public class ClientUIController extends ClientController implements Initializable {
    public static final int CONNECTION_STATUS_DISCONNECTED = 0;
    public static final int CONNECTION_STATUS_CONNECTING = 1;
    public static final int CONNECTION_STATUS_CONNECTED = 2;

    public static final int STATUS_ICON_SIZE = 10;
    public static final int STATUS_ICON_PADDNG = 10;

    @FXML MenuBar initalMenu;
    @FXML Menu Detections;
    @FXML Menu ConnectToServer;
    @FXML AnchorPane rootPane;
    @FXML MenuItem PerformanceItem;
    @FXML MenuItem Connect;
    @FXML Pane Graph1;
    @FXML Pane Graph2;
    @FXML Label Time;
    @FXML HBox connectionStatusPanel;
    @FXML VBox facePane;
    @FXML Button SelectIp;
    @FXML Button SelectPort;
    @FXML Ellipse head;
    @FXML Ellipse headOval;
    @FXML SubScene headScene;
    @FXML AnchorPane facialChartPane;
    @FXML AnchorPane affectiveChartPane;
    @FXML CheckBox interestCheckbox;
    @FXML CheckBox engagementCheckbox;
    @FXML CheckBox stressCheckbox;
    @FXML CheckBox relaxationCheckbox;
    @FXML CheckBox excitementCheckbox;
    @FXML CheckBox focusCheckbox;

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
    private Rectangle clench;
    private Rectangle clenchedTeeth;
    private Group clenchedMouth;

    private LineChart<Number,Number> facialChart;
    private ArrayList<XYChart.Series> facialSeriesArray;
    private int curFacialChartPos = 1;
    private LineChart<Number,Number> affectiveChart;
    private ArrayList<XYChart.Series> affectiveSeriesArray;
    private int curAffectiveChartPos = 0;

    /**
     * Creates entire Client pane with face, graphs, and connection status
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        long startTime = System.currentTimeMillis();
        ClientController cc = new ClientController();
        Group headGroup = initializeHead();
        facePane.getChildren().add(headGroup);

        ClientUIModel.getInstance().connectionStatusProperty().addListener(
                (observableValue, number, t1) -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int connStatus =
                                    ClientUIModel.getInstance().getConnectionStatus();
                            setConnectionStatus(connStatus);
                        }
                    });
                }
        );
        
        ClientUIModel.getInstance().timeElapsedProperty().addListener(
                (observable,number, t1) -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            double time = ClientUIModel.getInstance().getTimeElapsed();
                            updateTimeElapsed(String.valueOf(time));
                        }
                    });
                }
        );

        updateTimeElapsed("0.0");
        setConnectionStatus(CONNECTION_STATUS_DISCONNECTED);
        addReceiveDataListner();
        initFacialChart();
        initialAffectiveChart();
        addAffectiveChartCheckboxListener();
    }

    /**
     * Creates basic structure for facial expression graphs
     */
    private void initFacialChart(){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        facialChart = new LineChart<Number,Number>(xAxis,yAxis);
        facialChart.prefWidthProperty().bind(facialChartPane.widthProperty());
        facialChart.prefHeightProperty().bind(facialChartPane.heightProperty());
        facialChart.setLegendVisible(false);
        facialChart.getXAxis().setTickLabelsVisible(false);
        facialChart.getYAxis().setTickLabelsVisible(false);
        facialChart.setCreateSymbols(false);
        facialChart.setId("myFacialChart");

        facialChartPane.getChildren().add(facialChart);

        facialSeriesArray = new ArrayList<XYChart.Series>();

        for(int i = 0; i < 12; i++){
            XYChart.Series series = new XYChart.Series();
            facialSeriesArray.add(series);
            facialChart.getData().add(series);
            if(i <= 4){
                series.getData().add(new XYChart.Data(0, 11.25 - i));
            }else{
                series.getData().add(new XYChart.Data(0, 11 - i));
            }
        }
    }

    private void initialAffectiveChart(){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(1.0);
        yAxis.setLowerBound(0.0);
        affectiveChart = new LineChart<Number,Number>(xAxis,yAxis);
        affectiveChart.prefWidthProperty().bind(affectiveChartPane.widthProperty());
        affectiveChart.prefHeightProperty().bind(affectiveChartPane.heightProperty());
        affectiveChart.setLegendVisible(false);
        affectiveChart.setCreateSymbols(false);
        affectiveChart.setId("myAffectiveChart");

        affectiveChartPane.getChildren().add(affectiveChart);

        affectiveSeriesArray = new ArrayList<XYChart.Series>();

        for(int i = 0; i < 6; i++){
            XYChart.Series series = new XYChart.Series();
            affectiveSeriesArray.add(series);
            affectiveChart.getData().add(series);
        }

        interestCheckbox.setSelected(true);
        engagementCheckbox.setSelected(true);
        stressCheckbox.setSelected(true);
        relaxationCheckbox.setSelected(true);
        excitementCheckbox.setSelected(true);
        focusCheckbox.setSelected(true);
    }

    /**
     * Gives facial expression graphs data received from server.
     */
    private void updateFacialChart(){
        //Blink
        if(ClientUIModel.getInstance().getEyeData().getBlink()) {
            facialSeriesArray.get(0).getData().add(new XYChart.Data(curFacialChartPos, 11.75));
        } else {
            facialSeriesArray.get(0).getData().add(new XYChart.Data(curFacialChartPos, 11.25));
        }
        
        //Right Wink

        if(ClientUIModel.getInstance().getEyeData().getWinkRight()) {
            facialSeriesArray.get(1).getData().add(new XYChart.Data(curFacialChartPos, 10.75));
        } else{
            facialSeriesArray.get(1).getData().add(new XYChart.Data(curFacialChartPos, 10.25));
        }
        //Left Wink
        if(ClientUIModel.getInstance().getEyeData().getWinkLeft()) {
            facialSeriesArray.get(2).getData().add(new XYChart.Data(curFacialChartPos, 9.75));
        } else {
            facialSeriesArray.get(2).getData().add(new XYChart.Data(curFacialChartPos, 9.25));
        }
        
        //Look Right
        if(ClientUIModel.getInstance().getEyeData().getLookRight()) {
            facialSeriesArray.get(3).getData().add(new XYChart.Data(curFacialChartPos, 8.75));
        } else {
            facialSeriesArray.get(3).getData().add(new XYChart.Data(curFacialChartPos, 8.25));
        }
        //Look Left
        if(ClientUIModel.getInstance().getEyeData().getLookLeft()) {
            facialSeriesArray.get(4).getData().add(new XYChart.Data(curFacialChartPos, 7.75));
        } else {
            facialSeriesArray.get(4).getData().add(new XYChart.Data(curFacialChartPos, 7.25));
        }
        
        //Raise Brow
        facialSeriesArray.get(5).getData().add(new XYChart.Data(curFacialChartPos,
                    ClientUIModel.getInstance().getUpperFaceData().getRaiseBrow() + 6));
        //Furrow Brow
        facialSeriesArray.get(6).getData().add(new XYChart.Data(curFacialChartPos,
                ClientUIModel.getInstance().getUpperFaceData().getFurrowBrow() + 5));

        //Smile
        facialSeriesArray.get(7).getData().add(new XYChart.Data(curFacialChartPos,
                ClientUIModel.getInstance().getLowerFaceData().getSmile() + 4));

        //Clench
        facialSeriesArray.get(8).getData().add(new XYChart.Data(curFacialChartPos,
                ClientUIModel.getInstance().getLowerFaceData().getClench() + 3));

        //Right Smirk
        facialSeriesArray.get(9).getData().add(new XYChart.Data(curFacialChartPos,
                ClientUIModel.getInstance().getLowerFaceData().getSmirkRight() + 2));

        //Left Smirk
        facialSeriesArray.get(10).getData().add(new XYChart.Data(curFacialChartPos,
                ClientUIModel.getInstance().getLowerFaceData().getSmirkLeft() + 1));

        //Laugh
        facialSeriesArray.get(11).getData().add(new XYChart.Data(curFacialChartPos,
                ClientUIModel.getInstance().getLowerFaceData().getLaugh() + 0));

        curFacialChartPos++;
    }

    private void updateAffectiveChart(){
        //Interest
        affectiveSeriesArray.get(0).getData().add(new XYChart.Data(curAffectiveChartPos, ClientUIModel.getInstance().getEmoStates().getInterest()));
        //Engagement
        affectiveSeriesArray.get(1).getData().add(new XYChart.Data(curAffectiveChartPos, ClientUIModel.getInstance().getEmoStates().getEngagement()));
        //Stress
        affectiveSeriesArray.get(2).getData().add(new XYChart.Data(curAffectiveChartPos, ClientUIModel.getInstance().getEmoStates().getStress()));
        //Relaxation
        affectiveSeriesArray.get(3).getData().add(new XYChart.Data(curAffectiveChartPos, ClientUIModel.getInstance().getEmoStates().getRelaxation()));
        //Excitement
        affectiveSeriesArray.get(4).getData().add(new XYChart.Data(curAffectiveChartPos, ClientUIModel.getInstance().getEmoStates().getExcitement()));
        //Focus
        affectiveSeriesArray.get(5).getData().add(new XYChart.Data(curAffectiveChartPos, ClientUIModel.getInstance().getEmoStates().getFocus()));

        curAffectiveChartPos++;
    }

    public void addAffectiveChartCheckboxListener(){
        interestCheckbox.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if((boolean)oldValue){
                    affectiveChart.getData().get(0).getNode().setId("series-unselected");
                }else{
                    affectiveChart.getData().get(0).getNode().setId("series-selected");
                }
            }
        });
        engagementCheckbox.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if((boolean)oldValue){
                    affectiveChart.getData().get(1).getNode().setId("series-unselected");
                }else{
                    affectiveChart.getData().get(1).getNode().setId("series-selected");
                }
            }
        });
        stressCheckbox.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if((boolean)oldValue){
                    affectiveChart.getData().get(2).getNode().setId("series-unselected");
                }else{
                    affectiveChart.getData().get(2).getNode().setId("series-selected");
                }
            }
        });
        relaxationCheckbox.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if((boolean)oldValue){
                    affectiveChart.getData().get(3).getNode().setId("series-unselected");
                }else{
                    affectiveChart.getData().get(3).getNode().setId("series-selected");
                }
            }
        });
        excitementCheckbox.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if((boolean)oldValue){
                    affectiveChart.getData().get(4).getNode().setId("series-unselected");
                }else{
                    affectiveChart.getData().get(4).getNode().setId("series-selected");
                }
            }
        });
        focusCheckbox.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if((boolean)oldValue){
                    affectiveChart.getData().get(5).getNode().setId("series-unselected");
                }else{
                    affectiveChart.getData().get(5).getNode().setId("series-selected");
                }
            }
        });
    }

    public void addReceiveDataListner() {
        ClientUIModel.getInstance().addReceviveDataListner(new ClientUIModel.ReceviveDataListner() {
            @Override
            public void onReceiveData(EmotionalStatesData emoStates, EyeData eyeData, LowerFaceData lowerFaceData,
                    UpperFaceData upperFaceData) {
                //updateTimeElapsed();
                updateFaceExpressions();
                updateFacialChart();
                updateAffectiveChart();
            }
        });
    }

    /**
     * Updates timer with new time received from server via addReceiveDataListner()
     */
    public void updateTimeElapsed(String time) {
        Time.setText(time);
    }
    
    /**
     * Updates expressions on face with new values from server received via addReceiveDataListner()
     */
    public void updateFaceExpressions() {
        double eyebrowFurrowData = ClientUIModel.getInstance().getUpperFaceData().getFurrowBrow();
        double eyebrowRaiseData = ClientUIModel.getInstance().getUpperFaceData().getRaiseBrow();
        double smileData = ClientUIModel.getInstance().getLowerFaceData().getSmile();
        double laughData = ClientUIModel.getInstance().getLowerFaceData().getLaugh();
        double clenchData = ClientUIModel.getInstance().getLowerFaceData().getClench();
        double smirkLeftData = ClientUIModel.getInstance().getLowerFaceData().getSmirkLeft();
        double smirkRightData = ClientUIModel.getInstance().getLowerFaceData().getSmirkRight();
        boolean lookLeftData = ClientUIModel.getInstance().getEyeData().getLookLeft();
        boolean lookRightData = ClientUIModel.getInstance().getEyeData().getLookRight();
        boolean blinkData = ClientUIModel.getInstance().getEyeData().getBlink();
        boolean winkRightData = ClientUIModel.getInstance().getEyeData().getWinkRight();
        boolean winkLeftData = ClientUIModel.getInstance().getEyeData().getWinkLeft();

        if (eyebrowRaiseData != 0) {
            this.raiseBothEyebrows(eyebrowRaiseData);
        }
        
        if (eyebrowFurrowData != 0) {
            this.furrowBothEyebrows(eyebrowFurrowData);
        }
        
        if (smileData != 0) {
            this.setSmileAmount(smileData);
        }
        
        if (laughData != 0) {
            this.setLaughAmount(laughData);
        }
        
        if (clenchData != 0) {
            this.setClenchedAmount(clenchData);
        }
        
        if (smirkLeftData != 0) {
            this.setLeftSmirk(smirkLeftData);
        }
        
        if (smirkRightData != 0) {
            this.setRightSmirk(smirkRightData);
        }
        
        if (lookLeftData) {
            this.lookLeft();
        }
        
        if (lookRightData) {
            this.lookRight();
        }
        if(winkLeftData){
            this.closeEye(leftEye);
            this.openEye(rightEye);
        }
        if(winkRightData){
            this.closeEye(rightEye);
            this.openEye(leftEye);
        }
        if(blinkData){
            this.closeEye(leftEye);
            this.closeEye(rightEye);
        }
    }

    /**
     * Display an error dialog to the user and wait for acknowledgment
     * @param msg Message to be displayed on the dialog
     */
    protected static void showErrorDialog( String msg ){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        TextArea textArea = new TextArea(msg);
        textArea.setEditable(false);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    /**
     * Set the connection status in the UI
     * @param conn Current connection status
     */
    private void setConnectionStatus( int conn ){
        connectionStatusPanel.getChildren().clear();
        Text text;
        switch(conn){
            case CONNECTION_STATUS_DISCONNECTED:
                text = new Text("Disconnected");
                connectionStatusPanel.getChildren().add(
                        getStatusIconPanel(Color.RED));
                connectionStatusPanel.getChildren().add(text);
                break;
            case CONNECTION_STATUS_CONNECTED:
                text = new Text("Connected");
                connectionStatusPanel.getChildren().add(
                        getStatusIconPanel(Color.GREEN));
                connectionStatusPanel.getChildren().add(text);
                break;
            case CONNECTION_STATUS_CONNECTING:
                text = new Text("Connecting...");
                ProgressIndicator prog = new ProgressIndicator();
                prog.setMaxHeight(STATUS_ICON_SIZE * 2);
                prog.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                connectionStatusPanel.getChildren().add(prog);
                connectionStatusPanel.getChildren().add(text);
                break;
        }
    }

    /**
     * Helper to get a new panel containing a status icon
     * @param color Color of the status icon
     * @return HBox containing the status icon
     */
    private HBox getStatusIconPanel( Color color ){
        Circle disCircle = new Circle(STATUS_ICON_SIZE);
        disCircle.setFill(color);
        HBox cirPane = new HBox(disCircle);
        // Set padding on the right
        cirPane.setPadding(new Insets(0, STATUS_ICON_PADDNG,0,0));
        cirPane.setAlignment(Pos.CENTER);
        return cirPane;
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
        this.drawClenchedMouth(0);

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
        rightEye = new Ellipse(RIGHT_EYE_POSITION, EYES_POSITION_X, 12.0f, 18.0f);
        leftEye = new Ellipse(LEFT_EYE_POSITION, EYES_POSITION_X, 12.0f, 18.0f);
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
        leftEye.setRadiusY(18);
        rightEye.setCenterX(RIGHT_EYE_POSITION - 20);
        rightEye.setRadiusX(6);
        rightEye.setRadiusY(18);
    }

    /**
     * Draws both eyes looking to the right
     */
    public void lookRight() {
        leftEye.setCenterX(LEFT_EYE_POSITION + 20);
        leftEye.setRadiusX(6);
        leftEye.setRadiusY(18);
        rightEye.setCenterX(RIGHT_EYE_POSITION + 20);
        rightEye.setRadiusX(6);
        rightEye.setRadiusY(18);
    }

    /**
     * Draws one eye appearing as if closed
     *
     * @param eyeName	Eye that is currently closing
     */
    public void closeEye(Ellipse eyeName) {
        eyeName.setRadiusY(1);
    }


    /**
     * Draws one eye appearing as if opened
     *
     * @param eyeName	Eye that is currently opening
     */
    public void openEye(Ellipse eyeName) {
        eyeName.setRadiusY(18);
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
        clenchedMouth.setVisible(false);

        smileValue = smileValue * 100;
        mouth.setRadiusY(smileValue);
    }

    /**
     * Sets mouth to new left smirk amount
     *
     * @param smirkLeftAmount	controls size of left smirk, received from server
     */
    public void setLeftSmirk(double smirkLeftAmount) {
        // Reset after other mouth expressions
        mouth.setVisible(true);
        clenchedMouth.setVisible(false);

        smirkLeftAmount = smirkLeftAmount * 10 * 3;
        mouth.setRadiusY(10.0f);
        mouth.setRotate(smirkLeftAmount);
    }

    /**
     * Sets mouth to new right smirk amount
     *
     * @param smirkRightAmount	controls size of right smirk, received from server
     */
    public void setRightSmirk(double smirkRightAmount) {
        // Reset after other mouth expressions
        mouth.setVisible(true);
        clenchedMouth.setVisible(false);

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
        clenchedMouth.setVisible(false);

        laughValue = laughValue * 100;
        mouth.setFill(Color.BLACK);
        mouth.setRadiusY(laughValue);
    }

    /**
     * Creates mouth with clenched amount
     *
     * @param clenchValue	controls amount of clench expression, received from server
     */
    public void drawClenchedMouth(double clenchAmount) {
        clenchAmount = clenchAmount * 10 * 6;
        clench = new Rectangle(MOUTH_POSITION_X - 75, MOUTH_POSITION_Y, 150, clenchAmount);
        clench.setStroke(Color.BLACK);
        clench.setFill(null);
        clench.setStrokeWidth(3);

        clenchedTeeth = new Rectangle(MOUTH_POSITION_X - 75, MOUTH_POSITION_Y + 10, 150, 10);
        clenchedTeeth.setStroke(Color.BLACK);
        clenchedTeeth.setFill(null);
        clenchedTeeth.setStrokeWidth(3);

        clenchedMouth = new Group();
        clenchedMouth.getChildren().addAll(clench, clenchedTeeth);
        mouth.setVisible(false);
        clenchedMouth.setVisible(true);
    }
    
    /**
     * Updates mouth with clenched amount
     *
     * @param clenchValue   controls amount of clench expression, received from server
     */
    public void setClenchedAmount(double clenchAmount) {
        clenchAmount = clenchAmount * 10 * 6;
        clench.setHeight(clenchAmount);
        
        mouth.setVisible(false);
        clenchedMouth.setVisible(true);
    }
}
