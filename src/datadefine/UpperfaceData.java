package datadefine;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class UpperfaceData {

    public static final String RAISE_BROW = "Raise Brow";
    public static final String FURROW_BROW = "Furrow Brow";

    private double raiseBrow;
    private double furrowBrow;

    public UpperfaceData() {
        raiseBrow = 0;
        furrowBrow = 0;
    }

    public UpperfaceData(String jstr) {
        setFromString(jstr);
    }

    public UpperfaceData(JsonObject jobj) {
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

    public void setFromString(String jstr) {
        JsonReader reader = Json.createReader(new StringReader(jstr));
        JsonObject jobj = reader.readObject();
        reader.close();
        setFromJsonObject(jobj);
    }

    public void setFromJsonObject(JsonObject jobj) {
        setRaiseBrow(jobj.getJsonNumber(RAISE_BROW).doubleValue());
        setFurrowBrow(jobj.getJsonNumber(FURROW_BROW).doubleValue());
    }

    public JsonObject getJsonObject() {
        return Json.createObjectBuilder()
                .add(RAISE_BROW, getRaiseBrow())
                .add(FURROW_BROW, getFurrowBrow())
                .build();
    }
}
