package datadefine;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class LowerfaceData {

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

    public LowerfaceData() {
        smile = 0;
        clench = 0;
        smirkLeft = 0;
        smirkRight = 0;
        laugh = 0;
    }

    public LowerfaceData(String jstr) {
        setFromString(jstr);
    }

    public LowerfaceData(JsonObject jobj) {
        setFromJsonObject(jobj);
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

    public void setFromString(String jstr) {
        JsonReader reader = Json.createReader(new StringReader(jstr));
        JsonObject jobj = reader.readObject();
        reader.close();
        setFromJsonObject(jobj);
    }

    public void setFromJsonObject(JsonObject jobj) {
        setSmile(jobj.getJsonNumber(SMILE).doubleValue());
        setClench(jobj.getJsonNumber(CLENCH).doubleValue());
        setSmirkLeft(jobj.getJsonNumber(SMIRK_LEFT).doubleValue());
        setSmirkRight(jobj.getJsonNumber(SMIRK_RIGHT).doubleValue());
        setLaugh(jobj.getJsonNumber(LAUGH).doubleValue());
    }

    public JsonObject getJsonObject() {
        return Json.createObjectBuilder()
                .add(SMILE, getSmile())
                .add(CLENCH, getClench())
                .add(SMIRK_LEFT, getSmirkLeft())
                .add(SMIRK_RIGHT, getSmirkRight())
                .add(LAUGH, getLaugh())
                .build();
    }
}
