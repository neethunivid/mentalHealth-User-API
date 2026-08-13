package com.mentalhealthapi.helper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
//    public LocalDate deserialize2(JsonParser p, DeserializationContext ctxt)
//            throws IOException, JsonProcessingException {
//        int[] dateArray = p.getCodec().readValue(p, int[].class);
//        int year = dateArray[0];
//        int month = dateArray[1];
//        int day = dateArray[2];
//        return LocalDate.of(year, month, day);
//    }
    
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(jsonParser.readValueAs(String.class));
    }
    
}

