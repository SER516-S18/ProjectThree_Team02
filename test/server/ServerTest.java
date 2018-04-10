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

public class ServerTest {
    ServerController servController;
    ServerUIModel uiModel;
    EmotionalStatesData emoStates;
    EyeData eyeData;
    LowerFaceData lowerFaceData;
    UpperFaceData upperFaceData;

    @BeforeEach
    void setUp() {
        uiModel = ServerUIModel.getInstance();
        servController = ServerController.getInstance();
        emoStates = servController.getEmoStates();
        eyeData = servController.getEyeData();
        lowerFaceData = servController.getLowerFaceData();
        upperFaceData = servController.getUpperFaceData();
    }


    @Test
    void serverJSONEmoTest() {

        double relax = 0.1;
        double engage = 0.3;
        double excite = 0.7;
        double focus = 0.4;
        double interest = 0.8;
        double stress = 0.9;

        uiModel.setRelaxation(relax);
        uiModel.setEngagement(engage);
        uiModel.setExcitement(excite);
        uiModel.setFocus(focus);
        uiModel.setInterest(interest);
        uiModel.setStress(stress);


        // Get emo json obj
        JsonObject emoJ = getJsonObject(ServerController.JSON_EMOTION_KEY);
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.RELAXATION).doubleValue(),
                relax,
                0.01
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.ENGAGEMENT).doubleValue(),
                engage,
                0.01
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.EXCITEMENT).doubleValue(),
                excite,
                0.01
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.FOCUS).doubleValue(),
                focus,
                0.01
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.INTEREST).doubleValue(),
                interest,
                0.01
        );
        assertEquals(
                emoJ.getJsonNumber(EmotionalStatesData.STRESS).doubleValue(),
                stress,
                0.01
        );
    }

    @Test
    void serverJSONEyeTest() {

        // Before
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
