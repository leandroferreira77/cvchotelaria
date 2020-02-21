package br.com.cvc.corp.hotelaria.exceptions;

import br.com.cvc.corp.hotelaria.dto.DetalheErroDto;
import br.com.cvc.corp.hotelaria.dto.ResponseDto;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> validacaoException(final ValidationException ex, final WebRequest request) {
        return ex.buildResponse();
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> semResultadoException(final NotFoundException ex) {
        return ex.buildResponse();
    }

    @ExceptionHandler(value = {InfraEstrutureException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> infraestruturaException(final InfraEstrutureException ex) {
        return ex.buildResponse();
    }

    @ExceptionHandler(value = {BaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> jornadaException(final InfraEstrutureException ex) {
        return ex.buildResponse();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        final ResponseDto responseDefault = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDefault);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
                    final HttpStatus status, final WebRequest request) {

        final List<DetalheErroDto> erros = new ArrayList<>();

        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            erros.add(new DetalheErroDto(HttpStatus.BAD_REQUEST.value(), error.getField() + ": " + error.getDefaultMessage()));
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            erros.add(new DetalheErroDto(HttpStatus.BAD_REQUEST.value(), error.getObjectName() + ": " + error.getDefaultMessage()));
        }
        final ResponseDto responseDefault = new ResponseDto();

        responseDefault.setCodigo(HttpStatus.BAD_REQUEST.value());
        responseDefault.setMensagem(ex.getMessage());
        responseDefault.setErros(erros);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDefault);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status,
                    final WebRequest request) {
        final String error = ex.getValue() + " valor para " + ex.getPropertyName() + " deve ser do tipo " + ex.getRequiredType();
        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.BAD_REQUEST.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(error));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDefault);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers,
                    final HttpStatus status, final WebRequest request) {
        final String error = ex.getRequestPartName() + " URL incompleta";

        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.BAD_REQUEST.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(error));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDefault);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                    final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String error = ex.getParameterName() + " parametro não encontrado";
        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.BAD_REQUEST.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(error));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDefault);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        final String error = ex.getName() + " deve ser do tipo " + ex.getRequiredType().getName();
        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.BAD_REQUEST.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(error));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDefault);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers,
                    final HttpStatus status, final WebRequest request) {
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(error));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseDefault);
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                    final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" Metodo não suportado para essa requisição. Metodos suportados são ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.METHOD_NOT_ALLOWED.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(builder.toString()));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseDefault);
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers,
                    final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final ResponseDto responseDefault = new ResponseDto();
        responseDefault.setCodigo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        responseDefault.setMensagem(ex.getLocalizedMessage());
        responseDefault.getErros().add(new DetalheErroDto(builder.substring(0, builder.length() - 2)));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(responseDefault);
    }
}
