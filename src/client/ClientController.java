package client;

import model.*;
import server.ServerNetworkService;
import server.ServerUIModel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class ClientController {
    public static final String JSON_FACE_KEY = "Expressive";
    public static final String JSON_EMO_KEY = "Affective";
    public static final String JSON_INTERVAL_KEY = "EmoStateInterval";
    
    private static ClientController instance;
    private ClientUIController clientUIController;
    private ClientNetworkService<Void> networkThread;

    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;
    private EmoStateIntervalData emoStateIntervalData;

    /**
     * @return Singleton instance of ServerController
     */
    public static ClientController getInstance(){
        if( instance == null ){
            instance = new ClientController();
        }
        return instance;
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

    protected void decodeMessage(String jstr) {
        JsonReader reader = Json.createReader(new StringReader(jstr));
        JsonObject jobj = reader.readObject();
        reader.close();
        setFromJsonObject(jobj);
        updateReceivedData();
    }

    public ClientController(){
        // Create models
        emoStates = new EmotionalStatesData();
        eyeData = new EyeData();
        lowerFaceData = new LowerFaceData();
        upperFaceData = new UpperFaceData();
        emoStateIntervalData = new EmoStateIntervalData();
    }
    
    public void updateReceivedData() {
    	
    	ClientUIModel model = ClientUIModel.getInstance();
    	clientUIController = new ClientUIController();
    	double interval = emoStateIntervalData.getInterval();
    	model.setTimeElapsed(model.getTimeElapsed()+interval);
    	clientUIController.updateTimeElapsed();
    	
    }

    protected void connectToServer(String ip, String port){
        if( networkThread != null ){
            networkThread.cancel();
        }
        networkThread = new ClientNetworkService<>( ip, port );
        networkThread.restart();
    }

    private void setFromJsonObject(JsonObject jobj) {

        // Affective
        JsonObject emoJson = jobj.getJsonObject(JSON_EMO_KEY);
        emoStates.setInterest(emoJson.getJsonNumber(EmotionalStatesData.INTEREST).doubleValue());
        emoStates.setEngagement(emoJson.getJsonNumber(EmotionalStatesData.ENGAGEMENT).doubleValue());
        emoStates.setStress(emoJson.getJsonNumber(EmotionalStatesData.STRESS).doubleValue());
        emoStates.setRelaxation(emoJson.getJsonNumber(EmotionalStatesData.RELAXATION).doubleValue());
        emoStates.setExcitement(emoJson.getJsonNumber(EmotionalStatesData.EXCITEMENT).doubleValue());
        emoStates.setFocus(emoJson.getJsonNumber(EmotionalStatesData.FOCUS).doubleValue());

        // Face
        JsonObject faceJson = jobj.getJsonObject(JSON_FACE_KEY);
        eyeData.setBlink(faceJson.getBoolean(EyeData.BLINK));
        eyeData.setWinkLeft(faceJson.getBoolean(EyeData.WINK_LEFT));
        eyeData.setWinkRight(faceJson.getBoolean(EyeData.WINK_RIGHT));
        eyeData.setLookLeft(faceJson.getBoolean(EyeData.LOOK_LEFT));
        eyeData.setLookRight(faceJson.getBoolean(EyeData.LOOK_RIGHT));

        lowerFaceData.setSmile(faceJson.getJsonNumber(LowerFaceData.SMILE).doubleValue());
        lowerFaceData.setClench(faceJson.getJsonNumber(LowerFaceData.CLENCH).doubleValue());
        lowerFaceData.setSmirkLeft(faceJson.getJsonNumber(LowerFaceData.SMIRK_LEFT).doubleValue());
        lowerFaceData.setSmirkRight(faceJson.getJsonNumber(LowerFaceData.SMIRK_RIGHT).doubleValue());
        lowerFaceData.setLaugh(faceJson.getJsonNumber(LowerFaceData.LAUGH).doubleValue());

        upperFaceData.setRaiseBrow(faceJson.getJsonNumber(UpperFaceData.RAISE_BROW).doubleValue());
        upperFaceData.setFurrowBrow(faceJson.getJsonNumber(UpperFaceData.FURROW_BROW).doubleValue());
        
        JsonObject emoStateIntervalJson = jobj.getJsonObject(JSON_INTERVAL_KEY);
        emoStateIntervalData.setInterval(emoStateIntervalJson.getJsonNumber(EmoStateIntervalData.INTERVAL).doubleValue());
    }
}

