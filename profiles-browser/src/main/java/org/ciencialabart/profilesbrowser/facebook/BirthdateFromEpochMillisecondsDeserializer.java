package org.ciencialabart.profilesbrowser.facebook;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BirthdateFromEpochMillisecondsDeserializer extends StdDeserializer<LocalDate> {

    private static final long serialVersionUID = -3145777962838666528L;

    protected BirthdateFromEpochMillisecondsDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), ZoneId.of("Z")).toLocalDate();
    }
}
