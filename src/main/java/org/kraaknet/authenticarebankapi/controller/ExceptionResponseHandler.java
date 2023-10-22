package org.kraaknet.authenticarebankapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotAuthorizedException;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotAuthorizedException.class)
    public ResponseEntity<Object> notAuthorizedExceptionHandler(@NonNull NotAuthorizedException e,
                                                               @NonNull WebRequest request) {
        log.warn("notAuthorizedExceptionHandler({}, {})", e, request);

        var message = "Not authorized.";
        return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(@NonNull NotFoundException e,
                                                                @NonNull WebRequest request) {
        log.warn("notFoundExceptionHandler({}, {})", e, request);

        var message = "Not found.";
        return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


}
