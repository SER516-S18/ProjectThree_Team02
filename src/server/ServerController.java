package server;

import model.EmotionalStatesData;
import model.EyeData;
import model.LowerFaceData;
import model.UpperFaceData;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServerController {

    private static ServerController instance;
    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;
    private boolean isSendingData = false;
    public static final String JSON_FACE_KEY = "Expressive";
    public static final String JSON_EMO_KEY = "Affective";

    /**
     * @return Singleton instance of ServerController
     */
    public static ServerController getInstance(){
        if( instance == null ){
            instance = new ServerController();
        }
        return instance;
    }

    /**
     * @return true if server is sending data, false otherwise
     */
    public boolean isSendingData() {
        return isSendingData;
    }


    /**
     * @param sendingData true if server is sending data, false otherwise
     */
    public void setIsSendingData( boolean sendingData ) {
        isSendingData = sendingData;
    }

    /**
     * @return Server emotional state model
     */
    public EmotionalStatesData getEmoStates() {
        return emoStates;
    }

    /**
     * @return Server eye data model
     */
    public EyeData getEyeData() {
        return eyeData;
    }

    /**
     * @return Server lower face data model
     */
    public LowerFaceData getLowerFaceData() {
        return lowerFaceData;
    }

    /**
     * @return Server upper face data model
     */
    public UpperFaceData getUpperFaceData() {
        return upperFaceData;
    }

    private ServerController(){
        // Create models
        emoStates = new EmotionalStatesData();
        eyeData = new EyeData();
        lowerFaceData = new LowerFaceData();
        upperFaceData = new UpperFaceData();
    }

    /**
     * Format JSON message for sending to the clients
     * @return Json payload as a string for sending
     */
    protected String formatMessage() {
        JsonObject jsonToSend;
        // Affective
        JsonObjectBuilder emoJson = Json.createObjectBuilder()
                .add(EmotionalStatesData.INTEREST, emoStates.getInterest())
                .add(EmotionalStatesData.ENGAGEMENT, emoStates.getEngagement())
                .add(EmotionalStatesData.STRESS, emoStates.getStress())
                .add(EmotionalStatesData.EXCITEMENT, emoStates.getExcitement())
                .add(EmotionalStatesData.FOCUS, emoStates.getFocus());
        // Expressive
        JsonObjectBuilder faceJson = Json.createObjectBuilder()
                .add(EyeData.BLINK, eyeData.getBlink())
                .add(EyeData.WINK_LEFT, eyeData.getWinkLeft())
                .add(EyeData.WINK_RIGHT, eyeData.getWinkRight())
                .add(EyeData.LOOK_LEFT, eyeData.getLookLeft())
                .add(EyeData.LOOK_RIGHT, eyeData.getLookRight())
                .add(LowerFaceData.SMILE, lowerFaceData.getSmile())
                .add(LowerFaceData.CLENCH, lowerFaceData.getClench())
                .add(LowerFaceData.SMIRK_LEFT, lowerFaceData.getSmirkLeft())
                .add(LowerFaceData.SMIRK_RIGHT, lowerFaceData.getSmirkRight())
                .add(LowerFaceData.LAUGH, lowerFaceData.getLaugh())
                .add(UpperFaceData.RAISE_BROW, upperFaceData.getRaiseBrow())
                .add(UpperFaceData.FURROW_BROW, upperFaceData.getFurrowBrow());
        // TODO - status and frequency
        return Json.createObjectBuilder()
                .add(JSON_FACE_KEY, faceJson)
                .add(JSON_EMO_KEY, emoJson)
                .build().toString();
    }
}
