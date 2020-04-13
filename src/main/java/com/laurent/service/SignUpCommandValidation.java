package com.laurent.service;

import com.laurent.domain.InvalidReason;
import com.laurent.domain.SignUpCommand;
import com.laurent.infra.UserRepository;
import com.laurent.validation.ValidationStep;
import lombok.AllArgsConstructor;

import java.util.Optional;

public class SignUpCommandValidation {

    public static class CommandConstraintsValidationStep extends ValidationStep<SignUpCommand, InvalidReason> {
        @Override
        public Optional<InvalidReason> validate(SignUpCommand command) {
            return thenValidate(command);
        }
    }

    @AllArgsConstructor
    public static class EmailDuplicationValidationStep extends ValidationStep<SignUpCommand, InvalidReason> {
        private final UserRepository userRepository;

        @Override
        public Optional<InvalidReason> validate(SignUpCommand command) {
            if (userRepository.exist(command.getEmail())) {
                return InvalidReason.of("Email [%s] is already taken", command.getEmail());
            }
            return thenValidate(command);
        }
    }
}
