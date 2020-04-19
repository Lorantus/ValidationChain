package com.laurent.service;

import com.laurent.action.InvalidReason;
import com.laurent.action.OperationResult;
import com.laurent.domain.SignUpCommand;
import com.laurent.infra.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SignUpServiceTest {
    private SignUpService signUpService;

    @Before
    public void setUp() {
        UserRepository userRepository = new UserRepository();
        SignUpValidationService validatorService = new SignUpValidationService(userRepository);

        signUpService =  new SignUpService(validatorService, userRepository);
    }

    @Test
    public void doitRetournerLUtilisateurCreeSansMessageDeValidation() {
        // GIVEN
        SignUpCommand command = SignUpCommand.builder()
                .username("username").email("l@t.fr")
                .firstName("john").lastName("doe")
                .build();

        // WHEN
        OperationResult<UUID> result = signUpService.create(command);

        // THEN
        assertThat(result.isValid()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    public void doitRetournerLeMessageDErreurDeValidationSansCreerDUtilisateur() {
        // GIVEN
        SignUpCommand command = SignUpCommand.builder()
                .username("username").email("l@not.fr")
                .firstName("john").lastName("doe")
                .build();

        // WHEN
        OperationResult<UUID> result = signUpService.create(command);

        // THEN
        assertThat(result.isValid()).isFalse();
        assertThat(result.getValue()).isNull();
        assertThat(result.getInvalidReasons())
                .flatExtracting(InvalidReason::getReason)
                .containsExactly("Email [%s] is already taken");
    }
}