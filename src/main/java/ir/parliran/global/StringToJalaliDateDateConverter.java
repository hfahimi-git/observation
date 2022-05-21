package ir.parliran.global;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToJalaliDateDateConverter implements Converter<String, DatePair> {
    @Override
    public DatePair convert(String source) {
        try {
            return new DatePair(source);
        }
        catch (Exception ignored) {
            return null;
        }
    }
}
