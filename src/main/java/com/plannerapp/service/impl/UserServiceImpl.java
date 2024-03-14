package com.plannerapp.service.impl;

import com.plannerapp.model.dto.UserLoginBindingModel;
import com.plannerapp.model.dto.UserRegisterBindingModel;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.service.UserService;
import com.plannerapp.util.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final PasswordEncoder encoder;

    private final LoggedUser loggedUser;

    private String currentPassword;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder encoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.loggedUser = loggedUser;
    }

    @Override
    public void register(UserRegisterBindingModel userRegisterBindingModel) {

        this.currentPassword=userRegisterBindingModel.getPassword();
        User user = this.userRepository.findByUsernameOrEmail(userRegisterBindingModel.getUsername(), userRegisterBindingModel.getEmail());
        if (!Objects.isNull(user)) {
            return;
        }

        User user1 = this.mapper.map(userRegisterBindingModel, User.class);

        user1.setPassword(this.encoder.encode(userRegisterBindingModel.getPassword()));

        this.userRepository.save(user1);


    }

    @Override
    public boolean validateLoginUser(UserLoginBindingModel userLoginBindingModel) {
        User user = this.userRepository.findByUsername(userLoginBindingModel.getUsername());

        if (!Objects.isNull(user)) {
            if(encoder.matches(userLoginBindingModel.getPassword(),user.getPassword())){
                return true;
            }

        }
        return false;
    }

    @Override
    public void login(UserLoginBindingModel userLoginBindingModel) {
        if(validateLoginUser(userLoginBindingModel)){
            User user = this.userRepository.findByUsername(userLoginBindingModel.getUsername());
            this.loggedUser.setUsername(user.getUsername());
            this.loggedUser.setId(user.getId());


        }
    }

    @Override
    public void logout() {
        this.loggedUser.setId(null);
        this.loggedUser.setUsername(null);
        this.loggedUser.setAssignedTask(new HashSet<>());
    }

    @Override
    public boolean validateEmail(String email) {
        if(this.userRepository.findByEmail(email)==null){
            return  true;
        }

        return false;
    }

    @Override
    public boolean validateUsername(String username) {
        return this.userRepository.findByUsername(username)==null;
    }

    @Override
    public boolean validatePassword(String confirm) {
        if(this.currentPassword== confirm){
            return true;
        }
        return false;
    }
}
