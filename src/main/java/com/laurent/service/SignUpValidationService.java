package com.laurent.service;

import com.laurent.action.InvalidReason;
import com.laurent.action.OperationValidatorService;
import com.laurent.domain.SignUpCommand;
import com.laurent.infra.UserRepository;
import com.laurent.service.SignUpValidation.CommandConstraintsValidationStep;
import com.laurent.service.SignUpValidation.EmailDuplicationValidationStep;
import com.laurent.validation.Validator;
import com.laurent.validation.ValidatorStrategy;

import java.util.ArrayList;
import java.util.List;

import static com.laurent.service.SignUpValidationStrategy.FIRST_NAME;
import static com.laurent.service.SignUpValidationStrategy.USER_NAME;
import static java.util.Arrays.asList;

public class SignUpValidationService implements OperationValidatorService<SignUpCommand> {
    private final ValidatorStrategy<SignUpCommand, InvalidReason> validatorStrategy;

    public SignUpValidationService(UserRepository userRepository) {
        this.validatorStrategy = new ValidatorStrategy<>(new ArrayList<Validator<SignUpCommand, InvalidReason>>() {{
                add(new CommandConstraintsValidationStep()
                        .then(new EmailDuplicationValidationStep(userRepository)));
                addAll(asList(FIRST_NAME, USER_NAME));
            }});
    }

    @Override
    public List<InvalidReason> validate(SignUpCommand command) {
        return validatorStrategy.validate(command);
    }
}
