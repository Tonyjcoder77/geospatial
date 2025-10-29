package br.com.sccon.cleanach.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        var pd = ProblemDetail.forStatus(BAD_REQUEST);
        pd.setDetail(ex.getBindingResult().toString());
        return pd;
    }

    @ExceptionHandler(DateTimeParseException.class)
    ProblemDetail handleDate(DateTimeParseException ex) {
        var pd = ProblemDetail.forStatus(BAD_REQUEST);
        pd.setDetail("Datas devem estar no formato ISO (yyyy-MM-dd) ou 'dd/MM/yyyy' quando vindo no JSON.");
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handleIllegalArg(IllegalArgumentException ex) {
        var pd = ProblemDetail.forStatus(BAD_REQUEST);
        pd.setDetail(ex.getMessage());
        return pd;
    }

    @ExceptionHandler(ErrorResponseException.class)
    ProblemDetail handleErrorResponse(ErrorResponseException ex) {
        var pd = ProblemDetail.forStatus(ex.getStatusCode());
        pd.setDetail(ex.getBody() != null ? ex.getBody().getDetail() : ex.getMessage());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail fallback(Exception ex) {
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setDetail("Erro interno: " + ex.getMessage());
        return pd;
    }
}
