package server;

import javafx.beans.property.*;
import model.*;

/**
 * The Mordel in MVC for the Server. Manages all critical status of the server.
 * Provide APIs for updating and querying data.
 *
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
public class ServerUIModel {

    public static final double DEFAULT_EMO_STATE_INTERVAL = 0.25;

    private static ServerUIModel instance;

    private volatile boolean isSendingData = false;
    private volatile boolean sendOnce = false;
    
    private SimpleDoubleProperty emoStateInterval;
    private SimpleDoubleProperty timeElapsed;
    private SimpleBooleanProperty autoRest;
    // Expressive
    private SimpleStringProperty eyeDataType;
    private SimpleStringProperty lowerfaceDataType;
    private SimpleStringProperty upperfaceDataType;
    private SimpleBooleanProperty eyeDataValue;
    private SimpleDoubleProperty lowerfaceDataValue;
    private SimpleDoubleProperty upperfaceDataValue;
    // Affective
    private SimpleDoubleProperty interest;
    private SimpleDoubleProperty engagement;
    private SimpleDoubleProperty stress;
    private SimpleDoubleProperty relaxation;
    private SimpleDoubleProperty excitement;
    private SimpleDoubleProperty focus;

    private ServerUIModel() {
        emoStateInterval = new SimpleDoubleProperty(DEFAULT_EMO_STATE_INTERVAL);
        autoRest = new SimpleBooleanProperty(false);
        timeElapsed = new SimpleDoubleProperty(0);
        eyeDataType = new SimpleStringProperty(EyeData.BLINK);
        lowerfaceDataType = new SimpleStringProperty(LowerFaceData.SMILE);
        upperfaceDataType = new SimpleStringProperty(UpperFaceData.RAISE_BROW);
        eyeDataValue = new SimpleBooleanProperty(false);
        lowerfaceDataValue = new SimpleDoubleProperty(0);
        upperfaceDataValue = new SimpleDoubleProperty(0);
        interest = new SimpleDoubleProperty(0);
        engagement = new SimpleDoubleProperty(0);
        stress = new SimpleDoubleProperty(0);
        relaxation = new SimpleDoubleProperty(0);
        excitement = new SimpleDoubleProperty(0);
        focus = new SimpleDoubleProperty(0);
    }

    /**
     * @return Singleton instance of ServerUIModel
     */
    protected static ServerUIModel getInstance(){
        if( instance == null ){
            instance = new ServerUIModel();
        }
        return instance;
    }

    public boolean isSendOnce() {
        return sendOnce;
    }

    public void setSendOnce(boolean sendOnce) {
        this.sendOnce = sendOnce;
    }

    public boolean isSendingData() {
        return isSendingData;
    }

    public void setSendingData(boolean sendingData) {
        isSendingData = sendingData;
    }

    public double getEmoStateInterval() {
        return emoStateInterval.get();
    }

    public SimpleDoubleProperty emoStateIntervalProperty() {
        return emoStateInterval;
    }

    public void setEmoStateInterval(double emoStateInterval) {
        this.emoStateInterval.set(emoStateInterval);
    }

    public double getTimeElapsed() {
        return timeElapsed.get();
    }

    public SimpleDoubleProperty timeElapsedProperty() {
        return timeElapsed;
    }

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed.set(timeElapsed);
    }

    public String getEyeDataType() {
        return eyeDataType.get();
    }

    public SimpleStringProperty eyeDataTypeProperty() {
        return eyeDataType;
    }

    public void setEyeDataType(String eyeDataType) {
        this.eyeDataType.set(eyeDataType);
    }

    public String getLowerfaceDataType() {
        return lowerfaceDataType.get();
    }
    public SimpleStringProperty lowerfaceDataTypeProperty() {
        return lowerfaceDataType;
    }

    public void setLowerfaceDataType(String lowerfaceDataType) {
        this.lowerfaceDataType.set(lowerfaceDataType);
    }

    public String getUpperfaceDataType() {
        return upperfaceDataType.get();
    }

    public SimpleStringProperty upperfaceDataTypeProperty() {
        return upperfaceDataType;
    }

    public void setUpperfaceDataType(String upperfaceDataType) {
        this.upperfaceDataType.set(upperfaceDataType);
    }

    public boolean getEyeDataValue() {
        return eyeDataValue.get();
    }

    public SimpleBooleanProperty eyeDataValueProperty() {
        return eyeDataValue;
    }

    public void setEyeDataValue(boolean eyeDataValue) {
        this.eyeDataValue.set(eyeDataValue);
    }

    public double getLowerfaceDataValue() {
        return lowerfaceDataValue.get();
    }

    public SimpleDoubleProperty lowerfaceDataValueProperty() {
        return lowerfaceDataValue;
    }

    public void setLowerfaceDataValue(double lowerfaceDataValue) {
        this.lowerfaceDataValue.set(lowerfaceDataValue);
    }

    public double getUpperfaceDataValue() {
        return upperfaceDataValue.getValue();
    }

    public SimpleDoubleProperty upperfaceDataValueProperty() {
        return upperfaceDataValue;
    }

    public void setUpperfaceDataValue(double upperfaceDataValue) {
        this.upperfaceDataValue.set(upperfaceDataValue);
    }

    public boolean isAutoRest() {
        return autoRest.get();
    }

    public SimpleBooleanProperty autoRestProperty() {
        return autoRest;
    }

    public void setAutoRest(boolean autoRest) {
        this.autoRest.set(autoRest);
    }

    public double getInterest() {
        return interest.get();
    }

    public SimpleDoubleProperty interestProperty() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest.set(interest);
    }

    public double getEngagement() {
        return engagement.get();
    }

    public SimpleDoubleProperty engagementProperty() {
        return engagement;
    }

    public void setEngagement(double engagement) {
        this.engagement.set(engagement);
    }

    public double getStress() {
        return stress.get();
    }

    public SimpleDoubleProperty stressProperty() {
        return stress;
    }

    public void setStress(double stress) {
        this.stress.set(stress);
    }

    public double getRelaxation() {
        return relaxation.get();
    }

    public SimpleDoubleProperty relaxationProperty() {
        return relaxation;
    }

    public void setRelaxation(double relaxation) {
        this.relaxation.set(relaxation);
    }

    public double getExcitement() {
        return excitement.get();
    }

    public SimpleDoubleProperty excitementProperty() {
        return excitement;
    }

    public void setExcitement(double excitement) {
        this.excitement.set(excitement);
    }

    public double getFocus() {
        return focus.get();
    }

    public SimpleDoubleProperty focusProperty() {
        return focus;
    }

    public void setFocus(double focus) {
        this.focus.set(focus);
    }
}
