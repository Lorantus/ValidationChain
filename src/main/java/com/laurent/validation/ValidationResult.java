package com.laurent.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class ValidationResult<T> {
    private final T reason;

    public static <T> Optional<T> valid() {
        return Optional.empty();
    }

    public static <T> Optional<T> invalid(T reason) {
        return Optional.of(reason);
    }
}
