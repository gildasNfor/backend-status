package com.example.restservice.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {
    private int UserNumber;
    private String postTime;
    private String statusImageUrl;
    private String statusVideoUrl;
    private boolean isPublicStatus;
    private String statusText;
    private String statusCaption;
    private String disappearTime;
    private String duration;

}
