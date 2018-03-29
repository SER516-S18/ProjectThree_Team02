package datadefine;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ExpressiveData {

    public static final String  EYE_BROW_RAISE = "LookingRight";
    public static final String  LOOKING_RIGHT = "EyebrowRaise";
    public static final String  LOOKING_LEFT = "LookingLeft";
    public static final String  LOOKING_DOWN = "LookingDown";
    public static final String  LOOKING_UP = "LookingUp";
    public static final String  RIGHT_WINK = "RightWink";
    public static final String  LEFT_WINK = "LeftWink";
    public static final String  BLINK = "Blink";
    public static final String  EYES_OPEN = "EyesOpen";
    public static final String  SMAIL = "Smile";
    public static final String  CLENCH = "Clench";

    private double eyebrowRaise;
    private double lookingRight;
    private double lookingLeft;
    private double lookingDown;
    private double lookingUp;
    private double rightWink;
    private double leftWink;
    private double blink;
    private double eyesopen;
    private double smile;
    private double clench;

    public ExpressiveData() {
        eyebrowRaise = 0;
        lookingRight = 0;
        lookingLeft = 0;
        lookingDown = 0;
        lookingUp = 0;
        rightWink = 0;
        leftWink = 0;
        blink = 0;
        eyesopen = 0;
        smile = 0;
        clench = 0;
    }

    public ExpressiveData(String jstr) {
        setFromString(jstr);
    }

    public ExpressiveData(JsonObject jobj) {
        setFromJsonObject(jobj);
    }

    public double getEyebrowRaise() {
        return eyebrowRaise;
    }

    public void setEyebrowRaise(double eyebrowRaise) {
        this.eyebrowRaise = eyebrowRaise;
    }

    public double getLookingRight() {
        return lookingRight;
    }

    public void setLookingRight(double lookingRight) {
        this.lookingRight = lookingRight;
    }

    public double getLookingLeft() {
        return lookingLeft;
    }

    public void setLookingLeft(double lookingLeft) {
        this.lookingLeft = lookingLeft;
    }

    public double getLookingDown() {
        return lookingDown;
    }

    public void setLookingDown(double lookingDown) {
        this.lookingDown = lookingDown;
    }

    public double getLookingUp() {
        return lookingUp;
    }

    public void setLookingUp(double lookingUp) {
        this.lookingUp = lookingUp;
    }

    public double getRightWink() {
        return rightWink;
    }

    public void setRightWink(double rightWink) {
        this.rightWink = rightWink;
    }

    public double getLeftWink() {
        return leftWink;
    }

    public void setLeftWink(double leftWink) {
        this.leftWink = leftWink;
    }

    public double getBlink() {
        return blink;
    }

    public void setBlink(double blink) {
        this.blink = blink;
    }

    public double getEyesopen() {
        return eyesopen;
    }

    public void setEyesopen(double eyesopen) {
        this.eyesopen = eyesopen;
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

    public void setFromString(String jstr) {
        JsonReader reader = Json.createReader(new StringReader(jstr));
        JsonObject jobj = reader.readObject();
        reader.close();
        setFromJsonObject(jobj);
    }

    public void setFromJsonObject(JsonObject jobj) {
        setEyebrowRaise(jobj.getJsonNumber(EYE_BROW_RAISE).doubleValue());
        setLookingRight(jobj.getJsonNumber(LOOKING_RIGHT).doubleValue());
        setLookingLeft(jobj.getJsonNumber(LOOKING_LEFT).doubleValue());
        setLookingDown(jobj.getJsonNumber(LOOKING_DOWN).doubleValue());
        setLookingUp(jobj.getJsonNumber(LOOKING_UP).doubleValue());
        setRightWink(jobj.getJsonNumber(RIGHT_WINK).doubleValue());
        setLeftWink(jobj.getJsonNumber(LEFT_WINK).doubleValue());
        setBlink(jobj.getJsonNumber(BLINK).doubleValue());
        setEyesopen(jobj.getJsonNumber(EYES_OPEN).doubleValue());
        setSmile(jobj.getJsonNumber(SMAIL).doubleValue());
        setClench(jobj.getJsonNumber(CLENCH).doubleValue());
    }

    public JsonObject getJsonObject() {
        return Json.createObjectBuilder()
                .add(EYE_BROW_RAISE, getEyebrowRaise())
                .add(LOOKING_RIGHT, getLookingRight())
                .add(LOOKING_LEFT, getLookingLeft())
                .add(LOOKING_UP, getLookingUp())
                .add(RIGHT_WINK, getRightWink())
                .add(LEFT_WINK, getLeftWink())
                .add(BLINK, getBlink())
                .add(EYES_OPEN, getEyesopen())
                .add(SMAIL, getSmile())
                .add(CLENCH, getClench())
                .build();
    }
}