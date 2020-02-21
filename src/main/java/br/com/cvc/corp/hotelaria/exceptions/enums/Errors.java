package br.com.cvc.corp.hotelaria.exceptions.enums;

import br.com.cvc.corp.hotelaria.configs.logs.StructuredLog;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public enum Errors {
    ERROR001,
    ERROR002,
    ERROR003,
    ERROR004;

    public String getMessage(final Locale messageLocale) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/exceptions", messageLocale);

        try {
            return new String(resourceBundle.getString(name() + ".message").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            StructuredLog.builder().exception(ex.getClass());
        }

        return resourceBundle.getString(name() + ".message");
    }
}
