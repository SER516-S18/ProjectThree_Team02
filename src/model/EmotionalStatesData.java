package model;

/**
 * Wrapper class for emotional states Data
 * Data consists of interest, engagement, stress, relexation, excitement, and focus
 *
 * @author Team 2, SER 516
 * @version 1.0 April 10, 2018
 */
public class EmotionalStatesData {

    public static final String INTEREST = "Interest";
    public static final String ENGAGEMENT = "Engagement";
    public static final String STRESS = "Stress";
    public static final String RELAXATION = "Relaxation";
    public static final String EXCITEMENT = "Excitement";
    public static final String FOCUS = "Focus";

    private double interest;
    private double engagement;
    private double stress;
    private double relaxation;
    private double excitement;
    private double focus;

    public EmotionalStatesData() {
        reset();
    }

    /* reset all data to its default value */
    public void reset() {
        interest = 0;
        engagement = 0;
        stress = 0;
        relaxation = 0;
        excitement = 0;
        focus = 0;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getEngagement() {
        return engagement;
    }

    public void setEngagement(double engagement) {
        this.engagement = engagement;
    }

    public double getStress() {
        return stress;
    }

    public void setStress(double stress) {
        this.stress = stress;
    }

    public double getRelaxation() {
        return relaxation;
    }

    public void setRelaxation(double relaxation) {
        this.relaxation = relaxation;
    }

    public double getExcitement() {
        return excitement;
    }

    public void setExcitement(double excitement) {
        this.excitement = excitement;
    }

    public double getFocus() {
        return focus;
    }

    public void setFocus(double focus) {
        this.focus = focus;
    }
}
