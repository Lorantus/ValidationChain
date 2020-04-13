package com.laurent.validation;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorStrategyTest {
    @Test
    public void doitRetournerLesErreursDeLaChaineDeValidation() {
        // GIVEN
        ValidatorStrategy<Object, String> strategies = new ValidatorStrategy<>(asList(
                toValidate -> Optional.of("Error 1"),
                toValidate -> Optional.of("Error 2")));

        // WHEN
        List<String> results = strategies.validate(null);

        // THEN
        assertThat(results).hasSize(2)
                .containsExactly("Error 1", "Error 2");
    }
}