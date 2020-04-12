package com.laurent.infra;

public class UserRepository {
    public boolean exist(String email) {
        return email.endsWith("@not.fr");
    }
}
