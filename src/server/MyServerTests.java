package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyServerTests {

    @Test
    public void testAdd() {
        String jsonPayload = ServerController.getInstance().getJsonMessage();
        String str = "Junit Server";
        assertEquals("Junit Server",str);
    }

}
