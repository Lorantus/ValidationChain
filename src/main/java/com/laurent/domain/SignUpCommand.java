package com.laurent.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = "email")
@Getter
@Setter
@Builder
public class SignUpCommand {
    String firstName;
    String lastName;
    String email;
    String username;
    String rawPassword;

    public User getUser() {
        return new User();
    }
}
