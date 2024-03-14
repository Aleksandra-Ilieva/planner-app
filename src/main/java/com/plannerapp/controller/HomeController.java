package com.plannerapp.controller;

import com.plannerapp.model.dto.AssignedTaskDto;
import com.plannerapp.model.dto.TaskDto;
import com.plannerapp.service.TaskService;
import com.plannerapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private final TaskService taskService;

    public final LoggedUser loggedUser;

    public HomeController(TaskService taskService, LoggedUser loggedUser) {
        this.taskService = taskService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute
    private LoggedUser loggedUser(){
        return loggedUser;
    }



    @GetMapping("/")
    public String getIndexPage(){
        if(loggedUser.isLogged()){
            return  "redirect:/home";
        }



        return "index";

    }

    @GetMapping("/home")
    public String getHomePage(Model model){
        if(!loggedUser.isLogged()){
            return "redirect:/";
        }

        List<TaskDto> dtos = taskService.getAllTasks();
        List<AssignedTaskDto> assignedTaskDtos = taskService.getAllAssignedTask();
        model.addAttribute("taskList",dtos);
        model.addAttribute("assignedTaskDtos",assignedTaskDtos);
        return "home";

    }
}
