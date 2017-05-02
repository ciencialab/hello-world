package org.ciencialabart.breadwinner.util;

import com.vaadin.data.util.converter.Converter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDropTimeConverter implements Converter<String, Date> {

    @Override
    public Date convertToModel(String value, Class<? extends Date> targetType, Locale locale) throws ConversionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String convertToPresentation(Date value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return DateFormat.getDateInstance(DateFormat.DEFAULT, locale).format(value);
    }

    @Override
    public Class<Date> getModelType() {
        return Date.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
