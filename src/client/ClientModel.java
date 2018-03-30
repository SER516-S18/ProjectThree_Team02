package client;

import datadefine.EmotionalStatesData;
import datadefine.EyeData;
import datadefine.LowerfaceData;
import datadefine.UpperfaceData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ClientModel {

    private SimpleDoubleProperty timeElapsed;

    public ClientModel() {
        timeElapsed.set(0);
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
}
