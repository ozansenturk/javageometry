package com.example.geometry.data;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class SquareValidationErrorBuilder {
    public static SquareValidationError fromBindingErrors(Errors errors) {
        SquareValidationError error = new SquareValidationError("Validation failed. "
                + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
