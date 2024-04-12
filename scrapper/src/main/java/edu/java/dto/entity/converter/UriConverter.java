package edu.java.dto.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.net.URI;

@Converter(autoApply = true)
public class UriConverter implements AttributeConverter<URI, String> {
    @Override
    public String convertToDatabaseColumn(URI uri) {
        return uri.toString();
    }

    @Override
    public URI convertToEntityAttribute(String s) {
        return URI.create(s);
    }
}
