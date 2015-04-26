package ro.tekin.disertatie.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tekin on 5/18/14.
 */
public class TDateDeserializer extends JsonDeserializer<Date> {
    private static DateFormat formatter =
            new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            return formatter.parse(jsonParser.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
