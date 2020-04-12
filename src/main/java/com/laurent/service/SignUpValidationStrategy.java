package com.laurent.service;

import com.laurent.domain.SignUpCommand;
import com.laurent.domain.InvalidReason;
import com.laurent.validation.ValidationResult;
import com.laurent.validation.Validator;

import java.util.Optional;

public enum SignUpValidationStrategy implements Validator<SignUpCommand, InvalidReason> {
    USER_NAME {
        @Override
        public Optional<InvalidReason> validate(SignUpCommand command) {
            return command.getUsername().isEmpty() ?
                    ValidationResult.invalid(InvalidReason.of("User name can't be empty")) :
                    ValidationResult.valid();
        }
    },
    FIRST_NAME {
        @Override
        public Optional<InvalidReason> validate(SignUpCommand command) {
            return command.getFirstName().isEmpty() ?
                    ValidationResult.invalid(InvalidReason.of("First name can't be empty")) :
                    ValidationResult.valid();
        }
    }
}
