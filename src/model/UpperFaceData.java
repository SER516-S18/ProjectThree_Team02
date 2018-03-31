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
        raiseBrow = 0;
        furrowBrow = 0;
    }

    public UpperFaceData(String jstr) {
        setFromString(jstr);
    }

    public UpperFaceData(JsonObject jobj) {
        setFromJsonObject(jobj);
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

    // TODO - this should be in the client controller
    public void setFromString(String jstr) {
        JsonReader reader = Json.createReader(new StringReader(jstr));
        JsonObject jobj = reader.readObject();
        reader.close();
        setFromJsonObject(jobj);
    }

    // TODO - this should be in the client controller
    public void setFromJsonObject(JsonObject jobj) {
        setRaiseBrow(jobj.getJsonNumber(RAISE_BROW).doubleValue());
        setFurrowBrow(jobj.getJsonNumber(FURROW_BROW).doubleValue());
    }
}
