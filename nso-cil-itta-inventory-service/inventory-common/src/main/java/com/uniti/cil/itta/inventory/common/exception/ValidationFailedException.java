package com.uniti.cil.itta.inventory.common.exception;

import java.util.List;

public class ValidationFailedException extends RuntimeException {
    private final List<String> errors;

    public ValidationFailedException(String message, List<String> errors) {
        super(message);
        this.errors = List.copyOf(errors);
    }

    public List<String> getErrors() { return errors; }
}
