package org.kraaknet.authenticarebankapi.controller.exceptions;

import org.checkerframework.checker.nullness.qual.NonNull;

public class CreationFailedException extends RuntimeException{

    public CreationFailedException(@NonNull String message) {
        super(message);
    }
}
