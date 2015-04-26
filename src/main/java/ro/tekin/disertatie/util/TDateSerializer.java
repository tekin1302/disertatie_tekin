package ro.tekin.disertatie.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tekin on 5/18/14.
 */
public class TDateSerializer extends JsonSerializer<Date> {
    private static DateFormat formatter =
            new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void serialize(Date value, JsonGenerator gen,
                          SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        gen.writeString(formatter.format(value));
    }
}
