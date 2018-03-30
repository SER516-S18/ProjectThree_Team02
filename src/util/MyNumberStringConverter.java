package util;

import javafx.util.converter.NumberStringConverter;

public class MyNumberStringConverter extends NumberStringConverter {
    @Override
    public Number fromString(String value) {
        try {
            return super.fromString(value);
        } catch (RuntimeException e) {
            return 0;
        }
    }
}
