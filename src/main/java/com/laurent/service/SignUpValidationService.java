package com.laurent.service;

import com.laurent.domain.SignUpCommand;
import com.laurent.domain.InvalidReason;
import com.laurent.infra.UserRepository;
import com.laurent.service.SignUpCommandValidation.CommandConstraintsValidationStep;
import com.laurent.service.SignUpCommandValidation.EmailDuplicationValidationStep;
import com.laurent.validation.Validator;
import com.laurent.validation.ValidatorStrategy;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class SignUpValidationService {
    private final ValidatorStrategy<SignUpCommand, InvalidReason> validatorStrategy;

    public SignUpValidationService(UserRepository userRepository) {
        this.validatorStrategy = new ValidatorStrategy<>(new ArrayList<Validator<SignUpCommand, InvalidReason>>() {{
                add(new CommandConstraintsValidationStep()
                        .then(new EmailDuplicationValidationStep(userRepository)));
                addAll(asList(SignUpValidationStrategy.values()));
            }});
    }

    public List<InvalidReason> validate(SignUpCommand command) {
        return validatorStrategy.validate(command);
    }
}
