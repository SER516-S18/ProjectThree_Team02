package model;

/**
 * A model to hold the frequency of sending data from server to clients
 * This frequency is the time between requests.
 */
public class Frequency {

    public static final String FREQUENCY_KEY = "Frequency";
    private double frequency;

    /**
     * @return Frequency rate (in seconds) that server is sending data
     */
    public double getFrequency() {
        return frequency;
    }

    /**
     * @param frequency Frequency rate (in seconds) for server to send data
     */
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
