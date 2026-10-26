package com.uniti.cil.itta.inventory.common.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String type, String field, Object value) {
        super(type + " with " + field + "=" + value + " already exists");
    }
}
