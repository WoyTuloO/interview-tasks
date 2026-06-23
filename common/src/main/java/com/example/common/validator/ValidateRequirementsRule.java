package com.example.common.validator;

import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class ValidateRequirementsRule implements ValidationRule {
    private final Collection<?> materialRequirements;

    @Override
    public void validate() {
        if (materialRequirements == null || materialRequirements.isEmpty()) {
            throw new RuleValidationException();
        }
    }
}
