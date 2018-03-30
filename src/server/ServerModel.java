package server;

import datadefine.EmotionalStatesData;
import datadefine.EyeData;
import datadefine.LowerfaceData;
import datadefine.UpperfaceData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ServerModel {

    public static final double DEFAULT_EMO_STATE_INTERVAL = 0.25;

    private SimpleDoubleProperty emoStateInterval;
    private SimpleDoubleProperty timeElapsed;
    private SimpleBooleanProperty autoRest;

    private SimpleStringProperty eyeDataType;
    private SimpleStringProperty lowerfaceDataType;
    private SimpleStringProperty upperfaceDataType;
    private SimpleStringProperty emotionalStatesDataType;

    private SimpleDoubleProperty eyeDataValue;
    private SimpleDoubleProperty lowerfaceDataValue;
    private SimpleDoubleProperty upperfaceDataValue;
    private SimpleDoubleProperty emotionalStatesDataValue;

    public ServerModel() {
        emoStateInterval = new SimpleDoubleProperty(DEFAULT_EMO_STATE_INTERVAL);
        autoRest = new SimpleBooleanProperty(false);
        timeElapsed = new SimpleDoubleProperty((double)0);
        eyeDataType = new SimpleStringProperty(EyeData.BLINK);
        lowerfaceDataType = new SimpleStringProperty(LowerfaceData.SMILE);
        upperfaceDataType = new SimpleStringProperty(UpperfaceData.RAISE_BROW);
        emotionalStatesDataType = new SimpleStringProperty(EmotionalStatesData.INTEREST);
        eyeDataValue = new SimpleDoubleProperty((double)0);
        lowerfaceDataValue = new SimpleDoubleProperty((double)0);
        upperfaceDataValue = new SimpleDoubleProperty((double)0);
        emotionalStatesDataValue = new SimpleDoubleProperty((double)0);
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

    public double getEyeDataValue() {
        return eyeDataValue.get();
    }

    public SimpleDoubleProperty eyeDataValueProperty() {
        return eyeDataValue;
    }

    public void setEyeDataValue(double eyeDataValue) {
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
