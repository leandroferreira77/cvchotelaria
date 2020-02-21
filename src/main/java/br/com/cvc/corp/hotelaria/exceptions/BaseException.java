package br.com.cvc.corp.hotelaria.exceptions;

import br.com.cvc.corp.hotelaria.dto.DetalheErroDto;
import br.com.cvc.corp.hotelaria.dto.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5764036192252323794L;

    public BaseException() {
        this(null, null);
    }

    public BaseException(final String message) {
        super(message);
    }

    public BaseException(final Throwable cause) {
        super(cause);
    }

    public BaseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResponseEntity<?> buildResponse() {

        final ResponseDto responseDefault = new ResponseDto();

        responseDefault.setCodigo(getCodigo());
        responseDefault.setMensagem(getMensagem());
        responseDefault.setErros(getErros());

        return ResponseEntity.status(getStatusCode()).body(responseDefault);
    }

    protected abstract HttpStatus getStatusCode();

    public List<DetalheErroDto> getErros() {
        return new ArrayList<>();
    }

    protected Integer getCodigo() {
        return getStatusCode().value();
    }

    protected String getMensagem() {
        return getMessage();
    }

}
