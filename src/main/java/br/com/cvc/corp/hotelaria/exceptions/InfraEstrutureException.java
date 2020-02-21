package br.com.cvc.corp.hotelaria.exceptions;

import org.springframework.http.HttpStatus;

public class InfraEstrutureException extends BaseException {
    
    private static final long serialVersionUID = -7477166896086985527L;

    public InfraEstrutureException(final String message) {
        super(message);
    }

    public InfraEstrutureException(final Throwable cause) {
        super(cause);
    }

    public InfraEstrutureException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    protected HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
