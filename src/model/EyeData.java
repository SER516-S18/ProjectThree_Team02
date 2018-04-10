package model;

/**
 * Wrapper class for the eye data
 * Data consists of blink, winkLeft, winkRight, lookLeft, and lookRight
 *
 * @author Team 2, SER 516
 * @version 1.0 April 10, 2018
 */
public class EyeData {

    public static final String BLINK = "Blink";
    public static final String WINK_LEFT = "Wink Left";
    public static final String WINK_RIGHT = "Wink Right";
    public static final String LOOK_LEFT = "Look Left";
    public static final String LOOK_RIGHT = "Look Right";

    private boolean blink;
    private boolean winkLeft;
    private boolean winkRight;
    private boolean lookLeft;
    private boolean lookRight;

    public EyeData() {
        reset();
    }

    /* reset all data to its default value */
    public void reset() {
        blink = false;
        winkLeft = false;
        winkRight = false;
        lookLeft = false;
        lookRight = false;
    }

    public boolean getBlink() {
        return blink;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    public boolean getWinkLeft() {
        return winkLeft;
    }

    public void setWinkLeft(boolean winkLeft) {
        this.winkLeft = winkLeft;
    }

    public boolean getWinkRight() {
        return winkRight;
    }

    public void setWinkRight(boolean winkRight) {
        this.winkRight = winkRight;
    }

    public boolean getLookLeft() {
        return lookLeft;
    }

    public void setLookLeft(boolean lookLeft) {
        this.lookLeft = lookLeft;
    }

    public boolean getLookRight() {
        return lookRight;
    }

    public void setLookRight(boolean lookRight) {
        this.lookRight = lookRight;
    }
}
