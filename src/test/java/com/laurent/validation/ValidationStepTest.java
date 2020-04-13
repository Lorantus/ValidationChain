package com.laurent.validation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ValidationStepTest {
    @Test
    public void doitAppelerLaChaineDeValidation() {
        // GIVEN
        ValidationStep<Object, String> validationStep = new FixtureValidationStep()
                .then(new FixtureValidationStep("Error"));

        // WHEN
        Optional<String> result = validationStep.validate(null);

        // THEN
        assertThat(result).hasValueSatisfying(validationResult ->
                assertThat(validationResult).isEqualTo("Error"));
    }

    @Test
    public void doitArreterLaChaineDeValidation() {
        // GIVEN
        ValidationStep<Object, String> validationStep = new FixtureValidationStep();

        // WHEN
        Optional<String> result = validationStep.thenValidate(null);

        // THEN
        assertThat(result).isNotPresent();
    }


    @NoArgsConstructor
    @AllArgsConstructor
    private static class FixtureValidationStep extends ValidationStep<Object, String> {
        private String value;

        @Override
        public Optional<String> validate(Object toValidate) {
            return value == null ? Optional.empty() : Optional.of(value);
        }
    }
}