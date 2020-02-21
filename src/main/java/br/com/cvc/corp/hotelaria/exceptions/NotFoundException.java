package br.com.cvc.corp.hotelaria.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }

    @Override
    protected HttpStatus getStatusCode() {
        return HttpStatus.NO_CONTENT;
    }
}
