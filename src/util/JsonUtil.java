package util;

import javax.json.Json;

public class JsonUtil {

    public static String formatMessage(String message) {
        return Json.createObjectBuilder()
                .add("network/message", message)
                .build().toString();
    }
}
