package org.ciencialabart.profilesbrowser.person.gender;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class GenderFromStringDeserializer extends StdDeserializer<Gender> {

    private static final long serialVersionUID = -2785617359338932908L;
    
    protected GenderFromStringDeserializer() {
        super(Gender.class);
    }

    @Override
    public Gender deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException, JsonProcessingException {
        
        return Gender.valueOf(jsonParser.getText().toUpperCase());
    }
    
}
