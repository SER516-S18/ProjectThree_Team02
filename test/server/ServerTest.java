package server;

import model.EmotionalStatesData;
import model.EyeData;
import model.LowerFaceData;
import model.UpperFaceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Random;

/**
 * Unit tests for the server package
 *
 * @author Team 2
 */
public class ServerTest {
    public static double DOUBLE_DELTA = 0.01;

    private Random rand;
    private ServerController servController;
    private ServerUIModel uiModel;
    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;

    @BeforeEach
    void setUp() {
        uiModel = ServerUIModel.getInstance();
        servController = ServerController.getInstance();
        emoStates = servController.getEmoStates();
        eyeData = servController.getEyeData();
        lowerFaceData = servController.getLowerFaceData();
        upperFaceData = servController.getUpperFaceData();

        rand = new Random();
    }

    /**
     * Test emotion JSON data being sent from the server
     */
    @Test
    void serverJSONEmotionTest() {

        // Test initial values
        double testVal = 0.0;
        JsonObject emoJ = getJsonObject(ServerController.JSON_EMOTION_KEY);
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.RELAXATION).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.ENGAGEMENT).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.EXCITEMENT).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.FOCUS).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.INTEREST).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.STRESS).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );


        testVal = rand.nextDouble();

        uiModel.setRelaxation(testVal);
        uiModel.setEngagement(testVal);
        uiModel.setExcitement(testVal);
        uiModel.setFocus(testVal);
        uiModel.setInterest(testVal);
        uiModel.setStress(testVal);

        // Change emo data and make sure change is applied
        // in the JSON obj
        emoJ = getJsonObject(ServerController.JSON_EMOTION_KEY);
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.RELAXATION).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.ENGAGEMENT).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.EXCITEMENT).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.FOCUS).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.INTEREST).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.STRESS).doubleValue(),
                testVal,
                DOUBLE_DELTA
        );
    }

    /**
     * Test eye JSON data being sent from the server
     */
    @Test
    void serverJSONEyeTest() {

        // Make sure everything starts as false
        JsonObject faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertTrue(!faceJ.getBoolean(EyeData.BLINK));
        assertTrue(!faceJ.getBoolean(EyeData.WINK_LEFT));
        assertTrue(!faceJ.getBoolean(EyeData.WINK_RIGHT));
        assertTrue(!faceJ.getBoolean(EyeData.LOOK_LEFT));
        assertTrue(!faceJ.getBoolean(EyeData.LOOK_RIGHT));

        // Change eye data and make sure change is applied
        // in the JSON obj
        uiModel.setEyeDataType(EyeData.BLINK);
        uiModel.setEyeDataValue(true);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertTrue(faceJ.getBoolean(EyeData.BLINK));

        uiModel.setEyeDataType(EyeData.WINK_LEFT);
        uiModel.setEyeDataValue(true);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertTrue(faceJ.getBoolean(EyeData.WINK_LEFT));

        uiModel.setEyeDataType(EyeData.WINK_RIGHT);
        uiModel.setEyeDataValue(true);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertTrue(faceJ.getBoolean(EyeData.WINK_RIGHT));

        uiModel.setEyeDataType(EyeData.LOOK_LEFT);
        uiModel.setEyeDataValue(true);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertTrue(faceJ.getBoolean(EyeData.LOOK_LEFT));

        uiModel.setEyeDataType(EyeData.LOOK_RIGHT);
        uiModel.setEyeDataValue(true);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertTrue(faceJ.getBoolean(EyeData.LOOK_RIGHT));
    }

    /**
     * Test lower face JSON data being sent from the server
     */
    @Test
    void serverJSONLowerFaceTest() {

        // Make sure everything starts at 0.0
        double testVal = 0.0;
        JsonObject faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.SMILE).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.CLENCH).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.SMIRK_RIGHT).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.SMIRK_LEFT).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.LAUGH).doubleValue(),
                testVal,
                DOUBLE_DELTA);

        // Change lower face data and make sure change is applied
        // in the JSON obj
        testVal = rand.nextDouble();
        uiModel.setLowerfaceDataType(LowerFaceData.SMILE);
        uiModel.setLowerfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.SMILE).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        uiModel.setLowerfaceDataType(LowerFaceData.CLENCH);
        uiModel.setLowerfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.CLENCH).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        uiModel.setLowerfaceDataType(LowerFaceData.SMIRK_RIGHT);
        uiModel.setLowerfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.SMIRK_RIGHT).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        uiModel.setLowerfaceDataType(LowerFaceData.SMIRK_LEFT);
        uiModel.setLowerfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.SMIRK_LEFT).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        uiModel.setLowerfaceDataType(LowerFaceData.LAUGH);
        uiModel.setLowerfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(LowerFaceData.LAUGH).doubleValue(),
                testVal,
                DOUBLE_DELTA);

    }

    /**
     * Test upper face JSON data being sent from the server
     */
    @Test
    void serverJSONUpperFaceTest() {

        // Make sure everything starts at 0.0
        double testVal = 0.0;
        JsonObject faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(UpperFaceData.RAISE_BROW).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        assertEquals(
                faceJ.getJsonNumber(UpperFaceData.FURROW_BROW).doubleValue(),
                testVal,
                DOUBLE_DELTA);

        // Change upper face data and make sure change is applied
        // in the JSON obj
        testVal = rand.nextDouble();
        uiModel.setUpperfaceDataType(UpperFaceData.RAISE_BROW);
        uiModel.setUpperfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(UpperFaceData.RAISE_BROW).doubleValue(),
                testVal,
                DOUBLE_DELTA);
        uiModel.setUpperfaceDataType(UpperFaceData.FURROW_BROW);
        uiModel.setUpperfaceDataValue(testVal);
        faceJ = getJsonObject( ServerController.JSON_FACE_KEY );
        assertEquals(
                faceJ.getJsonNumber(UpperFaceData.FURROW_BROW).doubleValue(),
                testVal,
                DOUBLE_DELTA);

    }

    private JsonObject getJsonObject( String parent ){
        String jsonStr = servController.getJsonMessage();
        JsonObject json = parseJSON(jsonStr);
        return json.getJsonObject(parent);
    }

    private JsonObject parseJSON( String jsonStr ){
        JsonReader reader = Json.createReader( new StringReader( jsonStr ) );
        JsonObject jobj = reader.readObject();
        reader.close();
        return jobj;
    }
}
