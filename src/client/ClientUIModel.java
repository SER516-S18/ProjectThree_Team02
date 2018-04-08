package client;

import javafx.beans.property.SimpleDoubleProperty;
import model.*;
import server.ServerUIModel;

import java.util.ArrayList;

public class ClientUIModel {

    public interface ReceviveDataListner {

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
    private ArrayList<ReceviveDataListner> dataListners = new ArrayList<ReceviveDataListner>();

    /**
     * @return Singleton instance of ServerUIModel
     */
    protected static ClientUIModel getInstance() {
        if (instance == null) {
            instance = new ClientUIModel();
        }
        return instance;
    }

    public void addReceviveDataListner(ReceviveDataListner listner) {
        if (listner != null) {
            dataListners.add(listner);
        }
    }

    public void removeReceviveDataListner(ReceviveDataListner listner) {
        if (dataListners.contains(listner)) {
            dataListners.remove(listner);
        }
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
