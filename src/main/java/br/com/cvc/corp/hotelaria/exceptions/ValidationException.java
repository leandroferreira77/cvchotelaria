package br.com.cvc.corp.hotelaria.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import br.com.cvc.corp.hotelaria.dto.DetalheErroDto;

public class ValidationException extends BaseException {

    private static final long serialVersionUID = -4570655994360213008L;
    
    private List<DetalheErroDto> erros;

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final String message, final List<DetalheErroDto> erros) {
        super(message);
        this.erros = erros;
    }

    public ValidationException(final List<DetalheErroDto> erros) {
        super();
        this.erros = erros;
    }

    public ValidationException(DetalheErroDto... erros) {
        this.erros = nonNull(erros) ? asList(erros) : null;
    }

    @Override
    protected HttpStatus getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public List<DetalheErroDto> getErros() {
        return erros;
    }

    public boolean containsCodigoErro(Integer codigo) {
        return !isNull(this.erros) && this.erros.stream().anyMatch(e -> codigo.equals(e.getCodigo()));
    }

}
