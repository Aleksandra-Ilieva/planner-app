package com.plannerapp.service;

import com.plannerapp.model.dto.AssignedTaskDto;
import com.plannerapp.model.dto.TaskBindingModel;
import com.plannerapp.model.dto.TaskDto;

import java.util.List;

public interface TaskService {
    void addTask(TaskBindingModel taskBindingModel);

    List<TaskDto> getAllTasks();
    List<AssignedTaskDto> getAllAssignedTask();



    void assignedTask(Long id);

    void removeTask(Long id);

    void returnTask(Long id);
}
