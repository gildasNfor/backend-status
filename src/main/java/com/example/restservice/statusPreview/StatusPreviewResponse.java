package com.example.restservice.statusPreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusPreviewResponse {
    private int userNumber;
    private int numberOfStatus;
    private String userName;
    private String lastImageThumb;
    private String lastStatusTime;
}