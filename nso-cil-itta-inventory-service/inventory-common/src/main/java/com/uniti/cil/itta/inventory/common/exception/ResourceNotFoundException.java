package com.uniti.cil.itta.inventory.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String type, Object key) {
        super(type + " not found: " + key);
    }
}
