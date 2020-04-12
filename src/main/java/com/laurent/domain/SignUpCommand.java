package com.laurent.domain;

import lombok.*;

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
}
