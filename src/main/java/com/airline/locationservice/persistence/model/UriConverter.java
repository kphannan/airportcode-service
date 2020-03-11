package com.airline.locationservice.persistence.model;


import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UriConverter implements AttributeConverter<URI, String> {

    @Override
    public String convertToDatabaseColumn(URI uri) {
        // StringBuilder sb = new StringBuilder();
        // sb.append(color.getRed()).append(SEPARATOR)
        // .append(color.getGreen())
        // .append(SEPARATOR)
        // .append(color.getBlue())
        // .append(SEPARATOR)
        // .append(color.getAlpha());
        return uri.toString();
    }

    @Override
    public URI convertToEntityAttribute(String dbData) {
        // TODO Auto-generated method stub
        try {
            URI uri = new URI(dbData);
            return uri;
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
