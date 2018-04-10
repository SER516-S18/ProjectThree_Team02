package client;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.ArrayList;
import model.*;

/**
 * The Mordel in MVC for the client. Manages all critical status of the Client.
 * Provide APIs for updating and querying data.
 * Provide an interface to add listeners to the class to get notified when new data is received
 *
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
public class ClientUIModel {

    /**
     * Implement this interface and add to the class via addReceviveDataListner
     * to get notified when new data is received
     */
    public interface ReceviveDataListner {

        /**
         * Called when a new data is received from the server
         *
         * @param emoStates
         * @param eyeData
         * @param lowerFaceData
         * @param upperFaceData
         */
        public void onReceiveData(EmotionalStatesData emoStates, EyeData eyeData,
                                  LowerFaceData lowerFaceData, UpperFaceData upperFaceData);
    }

    private static ClientUIModel instance;

    private SimpleDoubleProperty timeElapsed = new SimpleDoubleProperty();
    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;
    private EmoStateIntervalData emoStateIntervalData;
    private SimpleIntegerProperty connectionStatus;
    private ArrayList<ReceviveDataListner> dataListners = new ArrayList<ReceviveDataListner>();

    private ClientUIModel() {
        connectionStatus = new SimpleIntegerProperty(
                ClientUIController.CONNECTION_STATUS_DISCONNECTED);
    }

    /**
     * @return Singleton instance of ServerUIModel
     */
    protected static ClientUIModel getInstance() {
        if (instance == null) {
            instance = new ClientUIModel();
        }
        return instance;
    }

    /**
     * Add an listerner to get notified when new data is received
     *
     * @param listner
     */
    public void addReceviveDataListner(ReceviveDataListner listner) {
        if (listner != null) {
            dataListners.add(listner);
        }
    }

    /**
     * Remove the listerner. Call it when you do not want to receive the new
     * data event anymore.
     *
     * @param listner
     */
    public void removeReceviveDataListner(ReceviveDataListner listner) {
        if (dataListners.contains(listner)) {
            dataListners.remove(listner);
        }
    }

    public int getConnectionStatus() {
        return connectionStatus.get();
    }

    public SimpleIntegerProperty connectionStatusProperty() {
        return connectionStatus;
    }

    public void setConnectionStatus(int connectionStatus) {
        this.connectionStatus.set(connectionStatus);
    }

    public EmotionalStatesData getEmoStates() {
        return emoStates;
    }

    public void setEmoStates(EmotionalStatesData emoStates) {
        this.emoStates = emoStates;
    }

    public EyeData getEyeData() {
        return eyeData;
    }

    public void setEyeData(EyeData eyeData) {
        this.eyeData = eyeData;
    }

    public LowerFaceData getLowerFaceData() {
        return lowerFaceData;
    }

    public void setLowerFaceData(LowerFaceData lowerFaceData) {
        this.lowerFaceData = lowerFaceData;
    }

    public UpperFaceData getUpperFaceData() {
        return upperFaceData;
    }

    public void setUpperFaceData(UpperFaceData upperFaceData) {
        this.upperFaceData = upperFaceData;
    }

    public EmoStateIntervalData getEmoStateIntervalData() {
        return emoStateIntervalData;
    }

    public void setEmoStateIntervalData(EmoStateIntervalData emoStateIntervalData) {
        this.emoStateIntervalData = emoStateIntervalData;
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

    /**
     * Called when a new data is received from the server
     *
     * @param emoStates
     * @param eyeData
     * @param lowerFaceData
     * @param upperFaceData
     * @param emoStateIntervalData
     */
    public void onReceiveData(EmotionalStatesData emoStates, EyeData eyeData,
                              LowerFaceData lowerFaceData, UpperFaceData upperFaceData,
                              EmoStateIntervalData emoStateIntervalData) {

        setEmoStateIntervalData(emoStateIntervalData);
        setEmoStates(emoStates);
        setEyeData(eyeData);
        setLowerFaceData(lowerFaceData);
        setUpperFaceData(upperFaceData);
        setEmoStateIntervalData(emoStateIntervalData);

        for (ReceviveDataListner listner : dataListners) {
            listner.onReceiveData(emoStates,
                    eyeData,
                    lowerFaceData,
                    upperFaceData);
        }
    }
}
