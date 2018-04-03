package model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class EmotionalStatesData {

    public static final String INTEREST = "Interest";
    public static final String ENGAGEMENT = "Engagement";
    public static final String STRESS = "Stress";
    public static final String RELAXATION = "Relaxation";
    public static final String EXCITEMENT = "Excitement";
    public static final String FOCUS = "Focus";

    private double Interest;
    private double Engagement;
    private double Stress;
    private double Relaxation;
    private double Excitement;
    private double Focus;

    public EmotionalStatesData() {
        reset();
    }

    public void reset() {
        Interest = 0;
        Engagement = 0;
        Stress = 0;
        Relaxation = 0;
        Excitement = 0;
        Focus = 0;
    }

    public double getInterest() {
        return Interest;
    }

    public void setInterest(double interest) {
        this.Interest = interest;
    }

    public double getEngagement() {
        return Engagement;
    }

    public void setEngagement(double engagement) {
        this.Engagement = engagement;
    }

    public double getStress() {
        return Stress;
    }

    public void setStress(double stress) {
        this.Stress = stress;
    }

    public double getRelaxation() {
        return Relaxation;
    }

    public void setRelaxation(double relaxation) {
        this.Relaxation = relaxation;
    }

    public double getExcitement() {
        return Excitement;
    }

    public void setExcitement(double excitement) {
        this.Excitement = excitement;
    }

    public double getFocus() {
        return Focus;
    }

    public void setFocus(double focus) {
        this.Focus = focus;
    }
}
