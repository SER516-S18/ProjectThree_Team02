package model;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class UpperFaceData {

    public static final String RAISE_BROW = "Raise Brow";
    public static final String FURROW_BROW = "Furrow Brow";

    private double raiseBrow;
    private double furrowBrow;

    public UpperFaceData() {
        reset();
    }

    public void reset() {
        raiseBrow = 0;
        furrowBrow = 0;
    }

    public double getRaiseBrow() {
        return raiseBrow;
    }

    public void setRaiseBrow(double raiseBrow) {
        this.raiseBrow = raiseBrow;
    }

    public double getFurrowBrow() {
        return furrowBrow;
    }

    public void setFurrowBrow(double furrowBrow) {
        this.furrowBrow = furrowBrow;
    }
}
