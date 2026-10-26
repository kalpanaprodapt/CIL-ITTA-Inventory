package com.uniti.cil.itta.inventory.common.dto;

import java.util.ArrayList;
import java.util.List;

public class BulkResult<T> {

    public static class Failure {
        private final int index;
        private final String message;

        public Failure(int index, String message) {
            this.index = index;
            this.message = message;
        }

        public int getIndex() { return index; }
        public String getMessage() { return message; }
    }

    private final List<T> succeeded = new ArrayList<>();
    private final List<Failure> failures = new ArrayList<>();

    public void addSuccess(T item) {
        succeeded.add(item);
    }

    public void addFailure(int index, String message) {
        failures.add(new Failure(index, message));
    }

    public List<T> getSucceeded() { return succeeded; }
    public List<Failure> getFailures() { return failures; }
    public boolean isComplete() { return failures.isEmpty(); }
}
