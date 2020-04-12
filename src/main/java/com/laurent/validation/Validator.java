package com.laurent.validation;

import java.util.Optional;

@FunctionalInterface
public interface Validator<T, K> {
    Optional<K> validate(T toValidate);
}
