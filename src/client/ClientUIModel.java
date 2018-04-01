package client;

import javafx.beans.property.SimpleDoubleProperty;

public class ClientUIModel {

    private SimpleDoubleProperty timeElapsed;

    public ClientUIModel() {
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
