package server;

import model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ServerUIModel {

    public static final double DEFAULT_EMO_STATE_INTERVAL = 0.25;

    private static ServerUIModel instance;

    private volatile boolean isSendingData = false;
    private volatile boolean sendOnce = false;
    private SimpleDoubleProperty emoStateInterval;
    private SimpleDoubleProperty timeElapsed;
    private SimpleBooleanProperty autoRest;
    private SimpleStringProperty eyeDataType;
    private SimpleStringProperty lowerfaceDataType;
    private SimpleStringProperty upperfaceDataType;
    private SimpleStringProperty emotionalStatesDataType;

    private SimpleBooleanProperty eyeDataValue;
    private SimpleDoubleProperty lowerfaceDataValue;
    private SimpleDoubleProperty upperfaceDataValue;
    private SimpleDoubleProperty emotionalStatesDataValue;

    private ServerUIModel() {
        emoStateInterval = new SimpleDoubleProperty(DEFAULT_EMO_STATE_INTERVAL);
        autoRest = new SimpleBooleanProperty(false);
        timeElapsed = new SimpleDoubleProperty(0);
        eyeDataType = new SimpleStringProperty(EyeData.BLINK);
        lowerfaceDataType = new SimpleStringProperty(LowerFaceData.SMILE);
        upperfaceDataType = new SimpleStringProperty(UpperFaceData.RAISE_BROW);
        emotionalStatesDataType = new SimpleStringProperty(EmotionalStatesData.INTEREST);
        eyeDataValue = new SimpleBooleanProperty(false);
        lowerfaceDataValue = new SimpleDoubleProperty(0);
        upperfaceDataValue = new SimpleDoubleProperty(0);
        emotionalStatesDataValue = new SimpleDoubleProperty(0);
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

    public String getEmotionalStatesDataType() {
        return emotionalStatesDataType.get();
    }

    public SimpleStringProperty emotionalStatesDataTypeProperty() {
        return emotionalStatesDataType;
    }

    public void setEmotionalStatesDataType(String emotionalStatesDataType) {
        this.emotionalStatesDataType.set(emotionalStatesDataType);
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
        return upperfaceDataValue.get();
    }

    public SimpleDoubleProperty upperfaceDataValueProperty() {
        return upperfaceDataValue;
    }

    public void setUpperfaceDataValue(double upperfaceDataValue) {
        this.upperfaceDataValue.set(upperfaceDataValue);
    }

    public double getEmotionalStatesDataValue() {
        return emotionalStatesDataValue.get();
    }

    public SimpleDoubleProperty emotionalStatesDataValueProperty() {
        return emotionalStatesDataValue;
    }

    public void setEmotionalStatesDataValue(double emotionalStatesDataValue) {
        this.emotionalStatesDataValue.set(emotionalStatesDataValue);
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
}
