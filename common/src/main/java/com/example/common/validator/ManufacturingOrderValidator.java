package com.example.common.validator;

import java.util.List;

public class ManufacturingOrderValidator {
    List<ValidationRule> rules;

    public ManufacturingOrderValidator(List<ValidationRule> rules) {
        this.rules = rules;
    }

    public void verify() {
        for (ValidationRule rule : rules) {
            rule.validate();
        }
    }
}
