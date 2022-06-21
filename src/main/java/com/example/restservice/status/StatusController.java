package com.example.restservice.status;

import com.example.restservice.scheduling.TaskDefinition;
import com.example.restservice.scheduling.TaskDefinitionBean;
import com.example.restservice.scheduling.TaskSchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private StatusService statusService;
    @Autowired
    private TaskSchedulingService taskSchedulingService;
    @Autowired
    private TaskDefinitionBean taskDefinitionBean;

    @PostMapping("/{userNumber}")
    public ResponseEntity<StatusResponse> addAStutus(
            @PathVariable int userNumber,
            @RequestParam(required = false) String statusCaption,
            @RequestParam(required = false) String statusText,
            @RequestParam(required = false) MultipartFile statusImage,
            @RequestParam(required = false) MultipartFile statusVideo,
            @RequestParam(required = false) boolean isPublicStatus,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String disappearTime) throws IOException {
        log.info("IN STATUS CONTROLLER:");
        log.info("statusCaption: "+ statusCaption );
        log.info("statusText: "+ statusText );
        log.info("isPublicStatus: "+ isPublicStatus );
        log.info("disappearTime: "+ disappearTime );
        log.info("statusImage: "+ statusImage );
        log.info("statusVideo: "+ statusVideo );
        log.info("duration: "+ duration);

        String downloadURl;
//        LocalDateTime thePostTime = LocalDateTime.now();

        Status status = statusService.addAStatus(userNumber, statusCaption, statusText, statusImage, statusVideo, isPublicStatus, disappearTime, duration);

        TaskDefinition taskDefinition = new TaskDefinition("0 40 8 * * ?", "delete status", status.getDisappearTime());
        taskDefinitionBean.setTaskDefinition(taskDefinition);
        taskSchedulingService.scheduleATask(disappearTime, taskDefinitionBean, taskDefinition.getCronExpression());

        StatusResponse statusResponse;
        if (statusVideo != null) {
             downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("status/view_video/")
                    .path(Integer.toString(status.getUserNumber()))
                    .toUriString() + "?postTime=" + status.getPostTime().toString();
            statusResponse = new StatusResponse(userNumber, status.getPostTime().toString(), null, downloadURl, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration());
        } else if (statusImage != null) {
             downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("status/view_image/")
                    .path(Integer.toString(status.getUserNumber()))
                    .toUriString() + "?postTime=" + status.getPostTime().toString();
            statusResponse = new StatusResponse(userNumber, status.getPostTime().toString(), downloadURl, null, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration());
        } else {
            statusResponse = new StatusResponse(userNumber, status.getPostTime().toString(), null, null, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration());
        }
        return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view_image/{userNumber}")
    public ResponseEntity<Resource> viewImage(@PathVariable int userNumber,
                                              @RequestParam() String postTime) { //MUST USE STRING HERE
        log.info("IN STATUS CONTROLLER GETTING IMAGE:");
        log.info("userNumber: "+ userNumber );
        log.info("postTime: "+ postTime );
        LocalDateTime theTime = LocalDateTime.parse(postTime);
        Status status = statusService.getStatusByUserAndPostTime(userNumber, theTime);
//        log.info("HERE IS THE GOTTEN STATUS: "+ status);
        int fileSize = status.getStatusImage().length;
        return ResponseEntity.ok()
                .header(CONTENT_TYPE, "image/jpg")
                .header(CONTENT_LENGTH, String.valueOf(fileSize))
                .body(new ByteArrayResource(status.getStatusImage()));
    }

    @GetMapping("/view_video/{userNumber}")
    public ResponseEntity<Resource> viewVideo(@PathVariable int userNumber,
                                              @RequestParam() String postTime) {
        LocalDateTime theTime = LocalDateTime.parse(postTime);

        Status status = statusService.getStatusByUserAndPostTime(userNumber, theTime);
        int fileSize = status.getStatusVideo().length;
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .header(String.valueOf(HttpStatus.PARTIAL_CONTENT))
//                .header(CONTENT_TYPE, "video/mp4")
//                .header(CONTENT_LENGTH, String.valueOf(fileSize))
//                .header(ACCEPT_RANGES, "bytes")
//                .header(CONTENT_RANGE, "bytes " + 0 + "-" + 10000 + "/" + fileSize)
//                .body(new ByteArrayResource(status.getStatusVideo()));
        return ResponseEntity.ok()
                .header(CONTENT_TYPE, "video/mp4")
                .header(CONTENT_LENGTH, String.valueOf(fileSize))
                .body(new ByteArrayResource(status.getStatusVideo()));
    }

    @GetMapping("/{userNumber}")
    public ResponseEntity<List<StatusResponse>> getStatusOfAUser(@PathVariable int userNumber){
        List<Status> statuses = statusService.getStatusOfAUser(userNumber);
        List<StatusResponse> statusResponses = new ArrayList<>();
        statuses.forEach(status -> {
            if(status.getStatusImage() != null ){
                String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("status/view_image/")
                        .path(Integer.toString(status.getUserNumber()))
                        .toUriString() + "?postTime=" + status.getPostTime().toString();
                statusResponses.add(new StatusResponse(status.getUserNumber(), status.getPostTime().toString(), downloadURl, null, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration()));

            }else if(status.getStatusVideo() != null){
                String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("status/view_video/")
                        .path(Integer.toString(status.getUserNumber()))
                        .toUriString() + "?postTime=" + status.getPostTime().toString();
                statusResponses.add(new StatusResponse(status.getUserNumber(), status.getPostTime().toString(),null , downloadURl, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration()));

            }else{
                statusResponses.add(new StatusResponse(status.getUserNumber(), status.getPostTime().toString(),null , null, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration()));

            }
        });
        return new ResponseEntity<>(statusResponses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StatusResponse>> getStatusOfAllUsers(){
        List<Status> statuses = statusService.getStatusOfAllUsers();
        List<StatusResponse> statusResponses = new ArrayList<>();
        statuses.forEach(status -> {
            if(status.getStatusImage() != null ){
                String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("status/view_image/")
                        .path(Integer.toString(status.getUserNumber()))
                        .toUriString() + "?postTime=" + status.getPostTime().toString();
                statusResponses.add(new StatusResponse(status.getUserNumber(), status.getPostTime().toString(), downloadURl, null, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration()));

            }else if(status.getStatusVideo() != null){
                String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("status/view_video/")
                        .path(Integer.toString(status.getUserNumber()))
                        .toUriString()  + "?postTime=" + status.getPostTime().toString();
                statusResponses.add(new StatusResponse(status.getUserNumber(), status.getPostTime().toString(),null , downloadURl, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration()));

            }else{
                statusResponses.add(new StatusResponse(status.getUserNumber(), status.getPostTime().toString(),null , null, status.isPublicStatus(), status.getStatusText(),status.getStatusCaption(), status.getDisappearTime(), status.getDuration()));

            }
        });
        return new ResponseEntity<>(statusResponses, HttpStatus.OK);
    }
}
