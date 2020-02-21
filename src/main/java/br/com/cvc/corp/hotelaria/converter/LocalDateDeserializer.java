package br.com.cvc.corp.hotelaria.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.nonNull;

public class LocalDateDeserializer extends StdDeserializer<LocalDate>{
    
    private static final long serialVersionUID = 1L;

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String as = parser.readValueAs(String.class);
        return nonNull(as) ? parse(as, ofPattern("dd/MM/yyyy")) : null;
    }

}
