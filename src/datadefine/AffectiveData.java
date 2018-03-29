package datadefine;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class AffectiveData {

    public static final String  MEDITATION = "Meditation";
    public static final String  ENGAGEMENT_BOREDOM = "EngagementBoredom";
    public static final String  EXCITEMENT_SHORT_TERM = "ExcitementShortTerm";
    public static final String  FRUSTRATION = "Frustration";
    public static final String  EXCITEMENT_LONG_TERM = "ExcitementLongTerm";

    private double meditation;
    private double engagementBoredom;
    private double excitementShortTerm;
    private double frustration;
    private double excitementLongTerm;

    public AffectiveData() {
        meditation = 0;
        engagementBoredom = 0;
        excitementShortTerm = 0;
        frustration = 0;
        excitementLongTerm = 0;
    }

    public double getMeditation() {
        return meditation;
    }

    public void setMeditation(double meditation) {
        this.meditation = meditation;
    }

    public double getEngagementBoredom() {
        return engagementBoredom;
    }

    public void setEngagementBoredom(double engagementBoredom) {
        this.engagementBoredom = engagementBoredom;
    }

    public double getExcitementShortTerm() {
        return excitementShortTerm;
    }

    public void setExcitementShortTerm(double excitementShortTerm) {
        this.excitementShortTerm = excitementShortTerm;
    }

    public double getFrustration() {
        return frustration;
    }

    public void setFrustration(double frustration) {
        this.frustration = frustration;
    }

    public double getExcitementLongTerm() {
        return excitementLongTerm;
    }

    public void setExcitementLongTerm(double excitementLongTerm) {
        this.excitementLongTerm = excitementLongTerm;
    }

    public void setFromString(String jstr) {
        JsonReader reader = Json.createReader(new StringReader(jstr));
        JsonObject jobj = reader.readObject();
        reader.close();
        setFromJsonObject(jobj);
    }

    public void setFromJsonObject(JsonObject jobj) {
        setMeditation(jobj.getJsonNumber(MEDITATION).doubleValue());
        setEngagementBoredom(jobj.getJsonNumber(ENGAGEMENT_BOREDOM).doubleValue());
        setExcitementShortTerm(jobj.getJsonNumber(EXCITEMENT_SHORT_TERM).doubleValue());
        setFrustration(jobj.getJsonNumber(FRUSTRATION).doubleValue());
        setExcitementLongTerm(jobj.getJsonNumber(EXCITEMENT_LONG_TERM).doubleValue());
    }

    public JsonObject getJsonObject() {
        return Json.createObjectBuilder()
                .add(MEDITATION, getMeditation())
                .add(ENGAGEMENT_BOREDOM, getEngagementBoredom())
                .add(EXCITEMENT_SHORT_TERM, getExcitementShortTerm())
                .add(FRUSTRATION, getFrustration())
                .add(EXCITEMENT_LONG_TERM, getExcitementLongTerm())
                .build();
    }
}
