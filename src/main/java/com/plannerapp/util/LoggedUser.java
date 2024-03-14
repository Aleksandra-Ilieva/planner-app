package com.plannerapp.util;

import com.plannerapp.model.dto.TaskDto;
import com.plannerapp.model.entity.Task;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionScope
@Component
public class LoggedUser {

    private Long id;

    private String username;

    private Set<TaskDto> assignedTask;

    public LoggedUser() {
        this.assignedTask= new HashSet<>();
    }

    public Set<TaskDto> getAssignedTask() {
        return assignedTask;
    }

    public void setAssignedTask(Set<TaskDto> assignedTask) {
        this.assignedTask = assignedTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogged(){
        if(this.id==null){
            return false;
        }

        return true;
    }
}
