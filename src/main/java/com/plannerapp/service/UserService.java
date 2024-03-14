package com.plannerapp.service;

import com.plannerapp.model.dto.UserLoginBindingModel;
import com.plannerapp.model.dto.UserRegisterBindingModel;

public interface UserService {
    void register(UserRegisterBindingModel userRegisterBindingModel);

    boolean validateLoginUser(UserLoginBindingModel userLoginBindingModel);

    void login(UserLoginBindingModel userLoginBindingModel);

    void logout();

    boolean validateEmail(String email);

    boolean validateUsername(String username);

    boolean validatePassword(String password);
}
