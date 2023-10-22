package org.kraaknet.authenticarebankapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotAuthorizedException;
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

    /**
     * Handlers like this should be there to prevent security sensitive details about the used libs to leak to the user.
     *
     * @param e Thrown type exception
     * @param request Triggering request
     * @return ResponseEntity with the details that can be given back safely to the end user.
     */
    @ExceptionHandler(value = NotAuthorizedException.class)
    public ResponseEntity<Object> notAuthorizedExceptionHandler(@NonNull NotAuthorizedException e,
                                                               @NonNull WebRequest request) {
        log.warn("notAuthorizedExceptionHandler({}, {})", e, request);

        var message = "Not authorized";
        return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
