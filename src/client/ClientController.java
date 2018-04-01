package client;

import model.*;
import server.ServerNetworkService;

public class ClientController {
    public static final String JSON_FACE_KEY = "Expressive";
    public static final String JSON_EMO_KEY = "Affective";

    private static ClientController instance;

    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;
    private Frequency freq;

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

    private ClientController(){
        // Create models
        emoStates = new EmotionalStatesData();
        eyeData = new EyeData();
        lowerFaceData = new LowerFaceData();
        upperFaceData = new UpperFaceData();
        freq = new Frequency();
    }
}
