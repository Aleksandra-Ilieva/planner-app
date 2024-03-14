package com.plannerapp.model.entity;

import com.plannerapp.model.enums.PriorityName;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "priorities")
public class Priority extends BaseEntity{

    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private PriorityName priorityName;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "priority")
    private Set<Task> tasks;

    public Priority() {
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public PriorityName getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(PriorityName priorityName) {
        this.priorityName = priorityName;
        setDescription(priorityName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void setDescription(PriorityName name) {
        String text = "";
        switch (name) {
            case LOW:
                text = "Should be fixed if time permits but can be postponed.";
                break;
            case IMPORTANT:
                text = "A core functionality that your product is explicitly supposed to perform is compromised.";
                break;
            case URGENT:
                text="An urgent problem that blocks the system use until the issue is resolved.";
                break;
        }


        this.description=text;

    }
}
