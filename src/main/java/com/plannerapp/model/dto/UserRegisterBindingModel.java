package com.plannerapp.model.dto;

import com.plannerapp.vallidation.annotation.ConfirmPassword;
import com.plannerapp.vallidation.annotation.UniqueEmail;
import com.plannerapp.vallidation.annotation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    //TODO make custom annotations for unique email and password(matching with confirmPassword)

    @Size(min = 3,max = 20,message = "Username length must be between 3 and 20 characters!")
    @NotNull
    @UniqueUsername
    private String username;

    @Email
    @NotBlank(message = "Email can not be empty")
    @UniqueEmail
    private String email;

    @Size(min = 3,max = 20,message = "Password length must be between 3 and 20 characters!")
    @NotNull
    private String password;

    @NotBlank
    @ConfirmPassword
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
