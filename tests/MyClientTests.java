package client;
import model.EmotionalStatesData;
import model.EyeData;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class MyClientTests {

    @Test
    public void testDecodeMessage() {
        try {
            ClientController clientController = new ClientController();
            String payload = "{" +
                    "\"Expressive\":{\"Blink\":false,\"Wink Left\":false," +
                    "\"Wink Right\":false,\"Look Left\":false,\"Look Right" +
                    "\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0," +
                    "\"Smirk Right\":0.0,\"Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow" +
                    "\":0.0},\"Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress" +
                    "\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,\"Focus\":0.0}," +
                    "\"Frequency\":3.0}\n";
            clientController.decodeMessage(payload);
            EmotionalStatesData emotionalStatesData = ClientUIModel.getInstance().getEmoStates();
            EyeData eyeData = ClientUIModel.getInstance().getEyeData();


            assertEquals(emotionalStatesData.getEngagement(), 0.0, 0.0);
            assertEquals(eyeData.getBlink(), false);
            assertEquals(eyeData.getWinkLeft(), false);
            assertEquals(emotionalStatesData.getExcitement(), 0.0, 0.0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
