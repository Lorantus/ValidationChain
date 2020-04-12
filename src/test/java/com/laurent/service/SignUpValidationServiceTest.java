package com.laurent.service;

import com.laurent.domain.SignUpCommand;
import com.laurent.domain.InvalidReason;
import com.laurent.infra.UserRepository;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class SignUpValidationServiceTest {

    @Test
    public void doitRetournerAucuneErreurDeValidation() {
        // GIVEN
        UserRepository userRepository = new UserRepository();

        SignUpCommand command = SignUpCommand.builder()
                .username("username").email("l@t.fr")
                .firstName("john").lastName("doe")
                .build();

        // WHEN
        List<InvalidReason> results = new SignUpValidationService(userRepository).validate(command);

        // THEN
        assertThat(results).isEmpty();
    }

    @Test
    public void doitRetournerLesErreursDeValidation() {
        // GIVEN
        UserRepository userRepository = new UserRepository();

        SignUpCommand command = SignUpCommand.builder()
                .username("username").email("l@not.fr")
                .firstName("").lastName("doe")
                .build();

        // WHEN
        List<InvalidReason> results = new SignUpValidationService(userRepository).validate(command);

        // THEN
        assertThat(results).hasSize(2)
                .extracting(InvalidReason::getReason, InvalidReason::getArgs)
                .containsExactly(
                        tuple("Email [%s] is already taken", new String[]{"l@not.fr"}),
                        tuple("First name can't be empty", new String[]{}));
    }
}