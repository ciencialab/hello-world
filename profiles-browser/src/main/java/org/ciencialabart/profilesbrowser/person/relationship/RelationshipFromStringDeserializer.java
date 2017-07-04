package org.ciencialabart.profilesbrowser.person.relationship;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class RelationshipFromStringDeserializer extends StdDeserializer<Relationship> {
    
    private static final long serialVersionUID = 1543859293077811410L;

    protected RelationshipFromStringDeserializer() {
        super(Relationship.class);
    }

    @Override
    public Relationship deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException, JsonProcessingException {
        
        return Relationship.valueOf(jsonParser.getText().toUpperCase());
    }
    
}
