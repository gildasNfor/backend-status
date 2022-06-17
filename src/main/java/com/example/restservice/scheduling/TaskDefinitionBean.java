package com.example.restservice.scheduling;


import com.example.restservice.status.Status;
import com.example.restservice.status.StatusRepository;
import com.example.restservice.status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskDefinitionBean implements Runnable {

    private TaskDefinition taskDefinition;
    private StatusService statusService;
    private StatusRepository statusRepository;

    @Autowired
    public  TaskDefinitionBean(StatusService statusService, StatusRepository statusRepository) {
        this.statusService = statusService;
        this.statusRepository = statusRepository;
    }

    @Override
    public void run() {

        System.out.println("Running action: " + taskDefinition.getActionType());
        System.out.println("With Data: " + taskDefinition.getDisappearTime());
        if (taskDefinition.getActionType().equals("delete status")) {

         Optional<Status> status = statusRepository.findByDisappearTime(taskDefinition.getDisappearTime());
            if(status.isPresent()){
                statusRepository.delete(status.get());

            }
        }
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
