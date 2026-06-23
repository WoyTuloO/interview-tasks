package com.example.common.validator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidateSubProductsRule implements ValidationRule {

    private final String productSku;

    @Override
    public void validate() {
        if (productSku == null || productSku.isBlank()) {
            throw new RuleValidationException();
        }
    }
}
