package com.laurent.service;

import com.laurent.domain.InvalidReason;
import com.laurent.domain.SignUpCommand;
import com.laurent.validation.Validator;

import java.util.Optional;

public enum SignUpValidationStrategy implements Validator<SignUpCommand, InvalidReason> {
    USER_NAME {
        @Override
        public Optional<InvalidReason> validate(SignUpCommand command) {
            return command.getUsername().isEmpty() ?
                    InvalidReason.of("User name can't be empty") :
                    Optional.empty();
        }
    },
    FIRST_NAME {
        @Override
        public Optional<InvalidReason> validate(SignUpCommand command) {
            return command.getFirstName().isEmpty() ?
                    InvalidReason.of("First name can't be empty") :
                    Optional.empty();
        }
    },
    NOT_USE_VALIDATOR {
        @Override
        public Optional<InvalidReason> validate(SignUpCommand toValidate) {
            return Optional.empty();
        }
    }
}
