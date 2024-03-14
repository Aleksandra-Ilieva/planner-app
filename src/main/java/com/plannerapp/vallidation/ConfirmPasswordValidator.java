package com.plannerapp.vallidation;

import com.plannerapp.service.UserService;
import com.plannerapp.vallidation.annotation.ConfirmPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword,String> {

    private final UserService userService;

    public ConfirmPasswordValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return this.userService.validatePassword(password);
    }
}
