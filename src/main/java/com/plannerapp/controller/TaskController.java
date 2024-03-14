package com.plannerapp.controller;

import com.plannerapp.model.dto.TaskBindingModel;
import com.plannerapp.model.dto.TaskDto;
import com.plannerapp.model.enums.PriorityName;
import com.plannerapp.service.TaskService;
import com.plannerapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    private final LoggedUser loggedUser;

    public TaskController(TaskService taskService, LoggedUser loggedUser) {
        this.taskService = taskService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/add/task")
    public String getAddTask() {
        if(loggedUser.isLogged()){
            return "task-add";
        }

        return "redirect:/users/login";


    }

    @PostMapping("/add/task")
    public String confirmTask(@Valid TaskBindingModel taskBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("taskBindingModel", taskBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.taskBindingModel", bindingResult);


            return "redirect:/add/task";
        }

        this.taskService.addTask(taskBindingModel);


        return "redirect:/home";

    }

    @GetMapping("/task/add/{id}")
    public String assignedTask(@PathVariable Long id) {
        if(loggedUser.isLogged()){
            this.taskService.assignedTask(id);
            return "redirect:/home";
        }

        return "redirect:/";

    }

    @GetMapping("/task/remove/{id}")
    public String removeTask(@PathVariable Long id) {
        if(loggedUser.isLogged()){
            this.taskService.removeTask(id);
            return "redirect:/home";
        }

        return "redirect:/";

    }

    @GetMapping("/task/return/{id}")
    public String returnTask(@PathVariable Long id) {
        if(loggedUser.isLogged()){
            this.taskService.returnTask(id);
            return "redirect:/home";
        }

        return "redirect:/";

    }


    @ModelAttribute
    public TaskBindingModel taskBindingModel() {
        return new TaskBindingModel();
    }

    @ModelAttribute
    public PriorityName[] priorityName() {
        return PriorityName.values();
    }

    @ModelAttribute
    public LoggedUser loggedUser() {
        return this.loggedUser;
    }


}
