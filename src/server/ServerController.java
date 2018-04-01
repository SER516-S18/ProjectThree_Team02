package server;

import model.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServerController {

    public static final String JSON_FACE_KEY = "Expressive";
    public static final String JSON_EMO_KEY = "Affective";
    private static ServerController instance;
    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;
    private Frequency freq;


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
     * @return Frequency model
     */
    public Frequency getFreqModel() {
        return freq;
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
        freq = new Frequency();
    }

    /**
     * Format JSON message for sending to the clients
     * @return Json payload as a string for sending
     */
    protected String getJsonMessage() {
        JsonObject jsonToSend;
        // Affective
        JsonObjectBuilder emoJson = Json.createObjectBuilder()
                .add(EmotionalStatesData.INTEREST, emoStates.getInterest())
                .add(EmotionalStatesData.ENGAGEMENT, emoStates.getEngagement())
                .add(EmotionalStatesData.STRESS, emoStates.getStress())
                .add(EmotionalStatesData.RELAXATION, emoStates.getRelaxation())
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
        return Json.createObjectBuilder()
                .add(JSON_FACE_KEY, faceJson)
                .add(JSON_EMO_KEY, emoJson)
                .add(Frequency.FREQUENCY_KEY, freq.getFrequency())
                .build().toString();
    }
}
