package com.example.common.validator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidateQuantityRule implements ValidationRule {
    private final int quantity;

    @Override
    public void validate() {
        if (quantity <= 0) {
            throw new RuleValidationException();
        }
    }
}
