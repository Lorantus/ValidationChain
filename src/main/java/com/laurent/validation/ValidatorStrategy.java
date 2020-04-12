package com.laurent.validation;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ValidatorStrategy<T, K> {
    private final List<Validator<T, K>> validators;

    public List<K> validate(T toValidate) {
        return validators.stream()
                .map(validator -> validator.validate(toValidate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }
}