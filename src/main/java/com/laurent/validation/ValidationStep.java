package com.laurent.validation;

import java.util.Optional;

public abstract class ValidationStep<T, K> implements Validator<T, K> {
    private ValidationStep<T, K> next;

    public ValidationStep<T, K> then(ValidationStep<T, K> next) {
        this.next = next;
        return next;
    }

    protected Optional<K> thenValidate(T toValidate) {
        return next == null ? Optional.empty() : next.thenValidate(toValidate);
    }
}
