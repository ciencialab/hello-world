package org.ciencialabart.journeyplanner.binding;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OffsetDateTimeToStringSerializer extends StdSerializer<OffsetDateTime> {

    private static final long serialVersionUID = -1657500649157947789L;

    public OffsetDateTimeToStringSerializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        jsonGenerator.writeString(value.toString());
    }

}
