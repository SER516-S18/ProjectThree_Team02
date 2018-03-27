package network;

import util.JsonUtil;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonPayloadEncoder implements Encoder.Text<JsonPayload> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(final JsonPayload message) throws EncodeException {
        return JsonUtil.formatMessage(message.getContent());
    }

}
