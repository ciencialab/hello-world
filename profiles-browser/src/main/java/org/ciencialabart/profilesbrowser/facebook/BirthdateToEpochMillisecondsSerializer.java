package org.ciencialabart.profilesbrowser.facebook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BirthdateToEpochMillisecondsSerializer extends StdSerializer<LocalDate> {

    private static final long serialVersionUID = 4309835961322663485L;
    
    protected BirthdateToEpochMillisecondsSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        jsonGenerator.writeNumber(localDate.atTime(LocalTime.MIDNIGHT).toInstant(ZoneOffset.UTC).toEpochMilli());
    }
    
}
