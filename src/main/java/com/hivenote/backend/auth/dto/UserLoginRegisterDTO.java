package com.hivenote.backend.auth.dto;

public class UserLoginRegisterDTO {
    private String name;
    private String lastName;
    private String email;
    private String cryptedPassword;

    public String getName() {
        return name;
    }

    public UserLoginRegisterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserLoginRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserLoginRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCryptedPassword() {
        return cryptedPassword;
    }

    public UserLoginRegisterDTO setCryptedPassword(String cryptedPassword) {
        this.cryptedPassword = cryptedPassword;
        return this;
    }
}
