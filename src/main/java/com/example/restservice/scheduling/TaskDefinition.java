package com.example.restservice.scheduling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskDefinition {

    private String cronExpression;
    private String actionType;
    private String disappearTime;
}

