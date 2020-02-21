package br.com.cvc.corp.hotelaria.exceptions;

import org.springframework.http.HttpStatus;

public class AutorizacaoException extends BaseException {
    
    private static final long serialVersionUID = 6658273272954737811L;

    public AutorizacaoException(final String message) {
        super(message);
    }

    public AutorizacaoException(final Throwable cause) {
        super(cause);
    }

    public AutorizacaoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    protected HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
