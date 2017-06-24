package org.ciencialabart.journeyplanner.binding;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class OffsetDateTimeFromStringDeserializer extends StdDeserializer<OffsetDateTime> {

    private static final long serialVersionUID = -3850437859030296110L;

    public OffsetDateTimeFromStringDeserializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        try {
            return OffsetDateTime.parse(jsonParser.getText());
        } catch (DateTimeParseException e) {
            throw new JsonParseException(jsonParser, "Parsed value has not OffsetDateTime String representation", e);
        }
    }

}
