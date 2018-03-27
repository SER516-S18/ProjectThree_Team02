package network;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;

public class JsonPayloadDecoder implements Decoder.Text<JsonPayload> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public JsonPayload decode(final String textMessage) throws DecodeException {
        JsonPayload message = new JsonPayload();
        JsonObject jsonObject = Json.createReader( new StringReader( textMessage ) ).readObject();
        message.setContent( jsonObject.getString( "network/message" ) );
        return message;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }
}
