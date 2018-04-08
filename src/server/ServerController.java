package server;

import model.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServerController {

    public static final String JSON_FACE_KEY = "Expressive";
    public static final String JSON_EMO_KEY = "Affective";
    public static final String JSON_INTERVAL_KEY = "EmoStateInterval";
    private static ServerController instance;
    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;
    private EmoStateIntervalData emoStateIntervalData;

    /**
     * @return Singleton instance of ServerController
     */
    public static ServerController getInstance(){
        if( instance == null ){
            instance = new ServerController();
        }
        return instance;
    }

    private ServerController(){
        emoStates = new EmotionalStatesData();
        eyeData = new EyeData();
        lowerFaceData = new LowerFaceData();
        upperFaceData = new UpperFaceData();
        emoStateIntervalData = new EmoStateIntervalData();
    }

    /**
     * Format JSON message for sending to the clients
     * @return Json payload as a string for sending
     */
    protected String getJsonMessage() {
        updateDataToSend();
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
        
        //EmoStateInterval
        JsonObjectBuilder emoStateIntervalJson = Json.createObjectBuilder()
        		.add(EmoStateIntervalData.INTERVAL, emoStateIntervalData.getInterval());
        
        return Json.createObjectBuilder()
                .add(JSON_FACE_KEY, faceJson)
                .add(JSON_EMO_KEY, emoJson)
                .add(JSON_INTERVAL_KEY, emoStateIntervalJson)
                .build().toString();
    }

    private void updateDataToSend() {
        emoStates.reset();
        eyeData.reset();
        lowerFaceData.reset();
        upperFaceData.reset();
        emoStateIntervalData.reset();

        ServerUIModel modelUI = ServerUIModel.getInstance();

        emoStates.setInterest(modelUI.getInterest());
        emoStates.setEngagement(modelUI.getEngagement());
        emoStates.setExcitement(modelUI.getExcitement());
        emoStates.setFocus(modelUI.getFocus());
        emoStates.setRelaxation(modelUI.getRelaxation());
        emoStates.setStress(modelUI.getStress());

        boolean eyeValue = modelUI.getEyeDataValue();
        switch (modelUI.getEyeDataType()) {
            case EyeData.BLINK: eyeData.setBlink(eyeValue); break;
            case EyeData.LOOK_LEFT: eyeData.setLookLeft(eyeValue); break;
            case EyeData.LOOK_RIGHT: eyeData.setLookRight(eyeValue); break;
            case EyeData.WINK_LEFT: eyeData.setWinkLeft(eyeValue); break;
            case EyeData.WINK_RIGHT: eyeData.setWinkRight(eyeValue); break;
        }

        double lowfaceValue = modelUI.getLowerfaceDataValue();
        switch (modelUI.getLowerfaceDataType()) {
            case LowerFaceData.SMILE: lowerFaceData.setSmile(lowfaceValue); break;
            case LowerFaceData.CLENCH: lowerFaceData.setClench(lowfaceValue); break;
            case LowerFaceData.LAUGH: lowerFaceData.setLaugh(lowfaceValue); break;
            case LowerFaceData.SMIRK_LEFT: lowerFaceData.setSmirkLeft(lowfaceValue); break;
            case LowerFaceData.SMIRK_RIGHT: lowerFaceData.setSmirkRight(lowfaceValue); break;
        }

        double upperfaceValue = modelUI.getUpperfaceDataValue();
        switch (modelUI.getUpperfaceDataType()) {
            case UpperFaceData.RAISE_BROW: upperFaceData.setRaiseBrow(upperfaceValue); break;
            case UpperFaceData.FURROW_BROW: upperFaceData.setFurrowBrow(upperfaceValue); break;
        }
        
        double interval = modelUI.getEmoStateInterval();
        emoStateIntervalData.setInterval(interval);
        
    }
}
