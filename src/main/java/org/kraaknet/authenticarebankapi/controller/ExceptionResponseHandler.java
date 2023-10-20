package org.kraaknet.authenticarebankapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    /**
     * Handlers like this should be there to prevent security sensitive details about the used libs to leak to the user.
     *
     * @param ex Thrown type exception
     * @param request Triggering request
     * @return ResponseEntity with the details that can be given back safely to the end user.
     */
    @ExceptionHandler(value = TypeMismatchException.class)
    public ResponseEntity<Object> typeMismatchExceptionHandler(@NonNull TypeMismatchException ex,
                                                               @NonNull WebRequest request) {
        log.warn("typeMismatchExceptionHandler({}, {})", ex, request);

        var message = "Type mismatch in request.";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
