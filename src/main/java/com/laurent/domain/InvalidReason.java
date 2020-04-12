package com.laurent.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "reason")
public class InvalidReason {
    private final String reason;
    private final String[] args;

    public static InvalidReason of(String reason, String... args) {
        return new InvalidReason(reason, args);
    }
}
