package com.laurent.infra;

import com.laurent.domain.User;

import java.util.UUID;

public class UserRepository {
    public boolean exist(String email) {
        return email.endsWith("@not.fr");
    }

    public UUID save(User user) {
        return UUID.randomUUID();
    }
}
