package com.example.common.validator;

public class RuleValidationException extends RuntimeException {
    public RuleValidationException() {
        super("Validation failed");
    }
}
