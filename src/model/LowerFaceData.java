package model;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class LowerFaceData {

    public static final String SMILE = "Smile";
    public static final String CLENCH = "Clench";
    public static final String SMIRK_LEFT = "Smirk Left";
    public static final String SMIRK_RIGHT = "Smirk Right";
    public static final String LAUGH = "Laugh";

    private double smile;
    private double clench;
    private double smirkLeft;
    private double smirkRight;
    private double laugh;

    public LowerFaceData() {
        reset();
    }

    public void reset() {
        smile = 0;
        clench = 0;
        smirkLeft = 0;
        smirkRight = 0;
        laugh = 0;
    }

    public double getSmile() {
        return smile;
    }

    public void setSmile(double smile) {
        this.smile = smile;
    }

    public double getClench() {
        return clench;
    }

    public void setClench(double clench) {
        this.clench = clench;
    }

    public double getSmirkLeft() {
        return smirkLeft;
    }

    public void setSmirkLeft(double smirkLeft) {
        this.smirkLeft = smirkLeft;
    }

    public double getSmirkRight() {
        return smirkRight;
    }

    public void setSmirkRight(double smirkRight) {
        this.smirkRight = smirkRight;
    }

    public double getLaugh() {
        return laugh;
    }

    public void setLaugh(double laugh) {
        this.laugh = laugh;
    }
}
