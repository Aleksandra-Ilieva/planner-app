package com.plannerapp.controller;

import com.plannerapp.model.dto.UserLoginBindingModel;
import com.plannerapp.model.dto.UserRegisterBindingModel;
import com.plannerapp.service.UserService;
import com.plannerapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final LoggedUser loggedUser;

    public UserController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }
    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @GetMapping("/logout")
    public String logout(){
        if(loggedUser.isLogged()){
            this.userService.logout();
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(){
        if(loggedUser.isLogged()){
            return  "redirect:/home";
        }
        return "register";

    }
    @GetMapping("/login")
    public String login(){
        if(loggedUser.isLogged()){
            return  "redirect:/home";
        }
        return "login";

    }





    @PostMapping("/register")

    public String confirmRegister(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel )
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }

        this.userService.register(userRegisterBindingModel);


        return "redirect:/users/login";

    }

    @PostMapping("/login")
    public String confirmLogin(@Valid UserLoginBindingModel userLoginBindingModel,BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,Model model){

        if(bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("userLoginBindingModel",userLoginBindingModel )
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);


            return "redirect:/users/login";
        }


        boolean validCredentials=this.userService.validateLoginUser(userLoginBindingModel);
        model.addAttribute("validCredentials",validCredentials);

        if(!validCredentials){
            redirectAttributes
                    .addFlashAttribute("validCredentials",false);


            return "redirect:/users/login";
        }

        this.userService.login(userLoginBindingModel);

        return "redirect:/home";
    }

}
