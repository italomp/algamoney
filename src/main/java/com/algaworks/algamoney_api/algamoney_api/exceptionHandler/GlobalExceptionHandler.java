package com.algaworks.algamoney_api.algamoney_api.exceptionHandler;

import com.algaworks.algamoney_api.algamoney_api.exceptionHandler.exceptions.PersonNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<CustomError> errorList = this.createErrorList(ex.getBindingResult());
        var result =  super.handleExceptionInternal(ex, errorList, headers, status, request);
        return result;
    }

    private List<CustomError> createErrorList(BindingResult bindingResult) {
        List<CustomError> errorList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            errorList.add(new CustomError(userMessage, developerMessage));
        }

        return errorList;
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            WebRequest request
    ) {
        String userMessage = messageSource.getMessage("operation.failed", null, LocaleContextHolder.getLocale());
        String devMessage = ExceptionUtils.getRootCauseMessage(exception);
        List<CustomError> errorList = Arrays.asList(new CustomError(userMessage, devMessage));
        return handleExceptionInternal(exception, errorList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ PersonNotFoundException.class })
    public ResponseEntity<Object> handlePersonNotFoundException(
            PersonNotFoundException exception,
            WebRequest request
    ) {
        String userMessage = messageSource.getMessage("person.not.found", null, LocaleContextHolder.getLocale());
        String devMessage = ExceptionUtils.getRootCauseMessage(exception);
        List<CustomError> errorList = Arrays.asList(new CustomError(userMessage, devMessage));
        return handleExceptionInternal(exception, errorList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @AllArgsConstructor
    @Getter
    private static class CustomError {
        String userMessage;
        String developerMessage;
    }
}
