package com.laurent.service;

import com.laurent.action.InvalidReason;
import com.laurent.action.OperationResult;
import com.laurent.domain.SignUpCommand;
import com.laurent.domain.User;
import com.laurent.infra.UserRepository;

import java.util.UUID;
import java.util.stream.Collectors;

public class SignUpService {
    private final SignUpValidationService signUpValidationService;
    private final UserRepository userRepository;

    public SignUpService(
            SignUpValidationService signUpValidationService,
            UserRepository userRepository)
    {
        this.signUpValidationService = signUpValidationService;
        this.userRepository = userRepository;
    }

    private UUID createUser(SignUpCommand command) {
        User user = command.getUser();
        return userRepository.save(user);
    }

    public OperationResult<UUID> create(SignUpCommand command) {
        return OperationResult.<UUID>of(signUpValidationService.validate(command))
                .flatMap(o -> OperationResult.ok(createUser(command)));
    }

    public UUID createGetter(SignUpCommand command) {
        return OperationResult.<UUID>of(signUpValidationService.validate(command))
                .flatMap(o -> OperationResult.ok(createUser(command)))
                .get();
    }

    public UUID createOrElse(SignUpCommand command) {
        return OperationResult.<UUID>of(signUpValidationService.validate(command))
                .flatMap(o -> OperationResult.ok(createUser(command)))
                .orElse(null);
    }

    public UUID createOrElseThrow(SignUpCommand command) {
        return OperationResult.<UUID>of(signUpValidationService.validate(command))
                .map(o -> OperationResult.ok(createUser(command)))
                .orElseThrow(operationResult -> new IllegalStateException(operationResult.getInvalidReasons().stream().
                        map(InvalidReason::getReason)
                        .collect(Collectors.joining(", "))));
    }

    public UUID save(SignUpCommand command) {
        return OperationResult.<UUID>of(signUpValidationService.validate(command))
                .map(o -> createUser(command));
    }

    public void createFromCommand(SignUpCommand command) {
        OperationResult.<UUID>of(signUpValidationService.validate(command))
                .ifValid(o -> createUser(command));
    }
}
