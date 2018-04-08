package model;

public class EmoStateIntervalData {

    private double interval;
    public static final String INTERVAL = "Interval";
    
    public EmoStateIntervalData() {
        reset();
    }
    
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
