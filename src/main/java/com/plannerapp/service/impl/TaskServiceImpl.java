package com.plannerapp.service.impl;

import com.plannerapp.model.dto.AssignedTaskDto;
import com.plannerapp.model.dto.TaskBindingModel;
import com.plannerapp.model.dto.TaskDto;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.service.TaskService;
import com.plannerapp.util.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private  final TaskRepository taskRepository;
    private final ModelMapper mapper;

    private final PriorityRepository priorityRepository;

    private final UserRepository userRepository;

    private final LoggedUser loggedUser;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper mapper, PriorityRepository priorityRepository, UserRepository userRepository, LoggedUser loggedUser) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.priorityRepository = priorityRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public void addTask(TaskBindingModel taskBindingModel) {

        Task task = this.mapper.map(taskBindingModel, Task.class);

        //Optional<User> user = this.userRepository.findById(loggedUser.getId());
        Priority priority = this.priorityRepository.findByPriorityName(taskBindingModel.getPriorityName());

        task.setPriority(priority);
      //  task.setUser(user.get());

        this.taskRepository.save(task);


    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> dtos = new ArrayList<>();

        for (Task task : tasks) {
            if(task.getUser()==null) {
                TaskDto dto = this.mapper.map(task, TaskDto.class);
                dtos.add(dto);
            }

        }

        return dtos;
    }

    @Override
    public List<AssignedTaskDto> getAllAssignedTask() {
        List<Task> tasks = taskRepository.findAll();
        List<AssignedTaskDto> dtos = new ArrayList<>();

        for (Task task : tasks) {
            if(task.getUser()!=null){
                AssignedTaskDto dto= this.mapper.map(task,AssignedTaskDto.class);
                dtos.add(dto);
            }

        }
        return dtos;
    }


    @Override
    public void assignedTask(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        Optional<User> user = this.userRepository.findById(loggedUser.getId());

        user.get().getAssignedTasks().add(task.get());
        TaskDto dto = this.mapper.map(task.get(),TaskDto.class);
        this.loggedUser.getAssignedTask().add(dto);


        task.get().setUser(user.get());
        this.taskRepository.save(task.get());

    }

    @Override
    public void removeTask(Long id) {
        this.taskRepository.deleteById(id);
    }

    @Override
    public void returnTask(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        task.get().setUser(null);
        this.taskRepository.save(task.get());
    }
}
