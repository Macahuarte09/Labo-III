package ar.edu.utn.frbb.tup.controller.handler;

import ar.edu.utn.frbb.tup.model.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TupResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TipoCuentaAlreadyExistsException.class, IllegalArgumentException.class,
            CantidadNegativaException.class, DatoIngresadoInvalidoException.class, TipoMonedaException.class,
            CuentaNoSoportadaException.class, NoAlcanzaException.class, CuentaOrigenYdestinoException.class})
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError();
        String nombreException = ex.getClass().getSimpleName();
        error.agregarInfoException(nombreException, ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ClienteAlreadyExistsException.class, ClienteMenorDeEdadException.class,
            CuentaAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError();
        String exceptionName = ex.getClass().getSimpleName();
        error.agregarInfoException(exceptionName, ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {ClienteNoEncontradoException.class, CuentaNoEncontradaException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError();
        String nombreException = ex.getClass().getSimpleName();
        error.agregarInfoException(nombreException, ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            String exceptionName = ex.getClass().getSimpleName();
            error.agregarInfoException(exceptionName, ex.getMessage());
            body = error;
        }
        return new ResponseEntity<>(body, headers, status);
    }
}