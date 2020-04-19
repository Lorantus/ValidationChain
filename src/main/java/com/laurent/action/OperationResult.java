package com.laurent.action;

import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

@Getter
public class OperationResult<T> {
    public static final OperationResult<?> EMPTY = OperationResult.of(null);

    private final List<InvalidReason> invalidReasons;
    private final T value;

    private OperationResult(List<InvalidReason> invalidReasons, T value) {
        this.invalidReasons = unmodifiableList(invalidReasons);
        this.value = value;
    }

    public static <U> OperationResult<U> ok(U result) {
        return new OperationResult<>(emptyList(), result);
    }

    public static <U> OperationResult<U> of(List<InvalidReason> invalidReasons) {
        return new OperationResult<>(invalidReasons, null);
    }

    public boolean isValid() {
        return invalidReasons.isEmpty();
    }

    public void ifValid(Consumer<T> consumer) {
        if(isValid()) {
            consumer.accept(value);
        }
    }

    public T get() {
        if (!isValid()) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public T orElse(T elseValue) {
        return isValid() ? value : elseValue;
    }

    public <X extends Throwable> T orElseThrow(Function<OperationResult<T>, ? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.apply(this);
        }
    }

    private List<InvalidReason> concatInvalidReasons(OperationResult<?> c1, OperationResult<?> c2) {
        return Stream.concat(c1.invalidReasons.stream(), c2.invalidReasons.stream())
            .collect(toList());
    }

    public <U> OperationResult<U> flatMap(Function<T, OperationResult<U>> mapper) {
        if(isValid()) {
            OperationResult<U> applied = mapper.apply(value);
            if(applied.isValid()) {
                return OperationResult.ok(applied.value);
            }
            return OperationResult.of(concatInvalidReasons(this, applied));
        }
        return OperationResult.of(invalidReasons);
    }

    public <U> U map(Function<OperationResult<T>, U> mapper) {
        return mapper.apply(this);
    }

    public OperationResult<T> concat(OperationResult<T> other) {
        return concatMerge(other, o -> {
            throw new IllegalArgumentException("Can't aggregate value " + this + " with " + other);
        });
    }

    public OperationResult<T> concatMerge(OperationResult<T> other, Function<T, T> merge) {
        if(!(isValid() || other.isValid())) {
            return OperationResult.of(concatInvalidReasons(this, other));
        }
        if(value == null || value.equals(other.value)) {
            return OperationResult.ok(other.value);
        }
        return OperationResult.ok(merge.apply(value));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationResult)) {
            return false;
        }
        OperationResult<?> other = (OperationResult<?>) o;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}