package model;

/**
 * Wrapper class for sending interval data
 *
 * @author Team 2, SER 516
 * @version 1.0 April 10, 2018
 */
public class EmoStateIntervalData {

    private double interval;
    public static final String INTERVAL = "Interval";

    public EmoStateIntervalData() {
        reset();
    }

    /* reset all data to its default value */
    public void reset() {
        interval = 0;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }
}
