package client;

import model.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Accepts and handles user input on Client UI
 * 
 * @version 1.0 April 10, 2018
 * @author Team 2, SER 516
 *
 */
public class ClientController {
    public static final String JSON_FACE_KEY = "Expressive";
    public static final String JSON_EMOTION_KEY = "Affective";
    public static final String JSON_INTERVAL_KEY = "EmoStateInterval";
    public static final String SERVER_LOCATION = ".." + File.pathSeparator + "server" + 
            File.pathSeparator + "Server.java";
    private static ClientController instance;
    private ClientNetworkService<Void> networkThread;

    /**
     * Provides access to a Singleton
     * 
     * @return Singleton instance of ClientController
     */
    public static ClientController getInstance(){
        if( instance == null ){
            instance = new ClientController();
        }
        return instance;
    }

    /**
     * Converts the JSON data and updates the ClientModel with data
     * 
     * @param jString   Message received
     */
    protected void decodeMessage(String jString) {
        JsonReader reader = Json.createReader(new StringReader(jString));
        JsonObject jobj = reader.readObject();
        reader.close();
        updateClientModel(jobj);
    }

    /**
     * Establishes connection with server, used for pop-up connection in Client
     * 
     * @param ip    IP address for server connection
     * @param port  Port number for server connection
     */
    protected void connectToServer(String ip, String port){
        if( networkThread != null ){
            networkThread.cancel();
        }
        networkThread = new ClientNetworkService<>( ip, port );
        networkThread.restart();
    }

    /**
     * Starts the server from Client application
     */
    protected void launchServer(){
        // Get the file in which this program is running
        // Default will be "client.jar"
        String path = ClientController.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .getPath();

        System.out.println("Starting server from " + path);
        ProcessBuilder pBuilder = new ProcessBuilder(
                System.getProperty("java.home") + "/bin/java",
                "-cp",
                path,
                "server.Server" );
        try {
            pBuilder.start();
        } catch (IOException e) {
            System.out.println("Error starting server");
            e.printStackTrace();
        }
    }

    /**
     * Sets values of variables with data received from user input
     * 
     * @param jObject   Input received from user
     */
    private void updateClientModel(JsonObject jObject) {
        // Create models
        EmotionalStatesData emoStates = new EmotionalStatesData();
        EyeData eyeData = new EyeData();
        LowerFaceData lowerFaceData = new LowerFaceData();
        UpperFaceData upperFaceData = new UpperFaceData();
        EmoStateIntervalData emoStateIntervalData = new EmoStateIntervalData();

        // Affective
        JsonObject emoJson = jObject.getJsonObject(JSON_EMOTION_KEY);
        emoStates.setInterest(emoJson.getJsonNumber(EmotionalStatesData.INTEREST).doubleValue());
        emoStates.setEngagement(emoJson.getJsonNumber(EmotionalStatesData.ENGAGEMENT).doubleValue());
        emoStates.setStress(emoJson.getJsonNumber(EmotionalStatesData.STRESS).doubleValue());
        emoStates.setRelaxation(emoJson.getJsonNumber(EmotionalStatesData.RELAXATION).doubleValue());
        emoStates.setExcitement(emoJson.getJsonNumber(EmotionalStatesData.EXCITEMENT).doubleValue());
        emoStates.setFocus(emoJson.getJsonNumber(EmotionalStatesData.FOCUS).doubleValue());

        // Face
        JsonObject faceJson = jObject.getJsonObject(JSON_FACE_KEY);
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
        
        JsonObject emoStateIntervalJson = jObject.getJsonObject(JSON_INTERVAL_KEY);
        emoStateIntervalData.
            setInterval(emoStateIntervalJson.getJsonNumber(EmoStateIntervalData.INTERVAL).doubleValue());

        double currTime = ClientUIModel.getInstance().getTimeElapsed();
        currTime += emoStateIntervalData.getInterval();
        ClientUIModel.getInstance().setTimeElapsed(currTime);
        ClientUIModel.getInstance().
            onReceiveData(emoStates, eyeData, lowerFaceData, upperFaceData, emoStateIntervalData);
    }
}