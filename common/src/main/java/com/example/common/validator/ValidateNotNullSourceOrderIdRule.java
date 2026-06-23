package com.example.common.validator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidateNotNullSourceOrderIdRule implements ValidationRule{

    private final String sourceOrderId;

    @Override
    public void validate() {
         if(sourceOrderId == null) {
             throw new RuleValidationException();
         }
    }
}
