package com.laurent.action;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "reason")
public class InvalidReason {
    private final String reason;
    private final String[] args;

    public static Optional<InvalidReason> of(String reason, String... args) {
        return Optional.of(new InvalidReason(reason, args));
    }
}
