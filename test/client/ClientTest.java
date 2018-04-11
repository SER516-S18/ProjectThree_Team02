package client;

import model.EmotionalStatesData;
import model.EyeData;
import model.LowerFaceData;
import model.UpperFaceData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the client package
 *
 * @author Team 2
 */
class ClientTest {
    public static double DOUBLE_EPSILON = 1e-10;

    private Random rand;
    private ClientController clientController;
    private ClientUIModel uiModel;
    private EmotionalStatesData emoStates;
    private EyeData eyeData;
    private LowerFaceData lowerFaceData;
    private UpperFaceData upperFaceData;

    @BeforeEach
    void setUp() {
        uiModel = ClientUIModel.getInstance();
        clientController = ClientController.getInstance();

        rand = new Random();
    }

    /**
     * Test Client recieving Emotion data
     */
    @Test
    void clientJSONEmotionTest() {

        //Dataset 1, default values
        String defaultValue = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(defaultValue);

        assertEquals(uiModel.getEmoStates().getEngagement(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getExcitement(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getFocus(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getInterest(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getRelaxation(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getStress(), 0.0, DOUBLE_EPSILON);

        //Dataset 2, random value
        double randomValue = rand.nextDouble();
        String dataset2 = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":" + randomValue + ",\"Engagement\":"+randomValue+
                    ",\"Stress\":"+randomValue+",\"Relaxation\":"+randomValue+",\"Excitement\":"+randomValue+","+
                    "\"Focus\":"+randomValue+"},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(dataset2);

        assertEquals(uiModel.getEmoStates().getEngagement(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getExcitement(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getFocus(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getInterest(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getRelaxation(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getEmoStates().getStress(), randomValue, DOUBLE_EPSILON);
    }

    /**
     * Test Client recieving Eye data
     */
    @Test
    void clientJSONEyeTest() {
        //Dataset 1, default values
        String defaultValue = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(defaultValue);

        assertTrue(!uiModel.getEyeData().getLookRight());
        assertTrue(!uiModel.getEyeData().getWinkLeft());
        assertTrue(!uiModel.getEyeData().getWinkRight());
        assertTrue(!uiModel.getEyeData().getBlink());
        assertTrue(!uiModel.getEyeData().getLookLeft());

        //Dataset 2, random value
        boolean randomValue = rand.nextBoolean();
        String dataset2 = "{\""+
                "Expressive\":{\"Blink\":"+randomValue+",\"Wink Left\":"+randomValue+",\"Wink Right\":"+randomValue+
                    ",\"Look Left\":"+randomValue+",\""+ "Look Right\":"+randomValue+
                    ",\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(dataset2);

        assertTrue(randomValue == uiModel.getEyeData().getLookRight());
        assertTrue(randomValue == uiModel.getEyeData().getWinkLeft());
        assertTrue(randomValue == uiModel.getEyeData().getWinkRight());
        assertTrue(randomValue == uiModel.getEyeData().getBlink());
        assertTrue(randomValue == uiModel.getEyeData().getLookLeft());
    }

    /**
     * Test Client recieving LowerFace data
     */
    @Test
    void clientJSONLowerFaceTest() {
        //Dataset 1, default values
        String defaultValue = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(defaultValue);

        assertEquals(uiModel.getLowerFaceData().getClench(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getLaugh(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getSmile(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getSmirkLeft(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getSmirkRight(), 0.0, DOUBLE_EPSILON);

        //Dataset 2, random value
        double randomValue = rand.nextDouble();
        String dataset2 = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":"+randomValue+",\"Clench\":"+randomValue+",\"Smirk Left\":"+
                    randomValue+",\"Smirk Right\":"+randomValue+",\""+
                    "Laugh\":"+randomValue+",\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(dataset2);

        assertEquals(uiModel.getLowerFaceData().getClench(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getLaugh(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getSmile(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getSmirkLeft(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getLowerFaceData().getSmirkRight(), randomValue, DOUBLE_EPSILON);
    }
    /**
     * Test Client recieving UpperFace data
     */
    @Test
    void clientJSONUpperFaceTest() {
        //Dataset 1, default values
        String defaultValue = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":0.0,\"Furrow Brow\":0.0},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(defaultValue);

        assertEquals(uiModel.getUpperFaceData().getFurrowBrow(), 0.0, DOUBLE_EPSILON);
        assertEquals(uiModel.getUpperFaceData().getRaiseBrow(), 0.0, DOUBLE_EPSILON);

        //Dataset 2, random value
        double randomValue = rand.nextDouble();
        String dataset2 = "{\""+
                "Expressive\":{\"Blink\":false,\"Wink Left\":false,\"Wink Right\":false,\"Look Left\":false,\""+
                    "Look Right\":false,\"Smile\":0.0,\"Clench\":0.0,\"Smirk Left\":0.0,\"Smirk Right\":0.0,\""+
                    "Laugh\":0.0,\"Raise Brow\":"+randomValue+",\"Furrow Brow\":"+randomValue+"},\"" +
                "Affective\":{\"Interest\":0.0,\"Engagement\":0.0,\"Stress\":0.0,\"Relaxation\":0.0,\"Excitement\":0.0,"+
                    "\"Focus\":0.0},\""+
                "EmoStateInterval\":{\"Interval\":0.25}}\n";

        clientController.decodeMessage(dataset2);

        assertEquals(uiModel.getUpperFaceData().getFurrowBrow(), randomValue, DOUBLE_EPSILON);
        assertEquals(uiModel.getUpperFaceData().getRaiseBrow(), randomValue, DOUBLE_EPSILON);
    }
}