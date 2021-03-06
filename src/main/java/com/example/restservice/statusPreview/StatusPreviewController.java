package com.example.restservice.statusPreview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/status_preview")
@Slf4j
public class StatusPreviewController {
    @Autowired
    private StatusPreviewService statusPreviewService;

    @GetMapping
    public ResponseEntity<List<StatusPreviewResponse>> getAllUsersStatusPreview() {
        List<StatusPreview> statusPreviews = statusPreviewService.getAllStatusPreviews();
        List<StatusPreviewResponse> statusPreviewResponses = new ArrayList<>();
        statusPreviews.forEach(statusPreview -> {
            String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/user/view/")
                    .path(Integer.toString(statusPreview.getUserNumber()))
                    .toUriString();

            StatusPreviewResponse statusPreviewResponse = new StatusPreviewResponse(statusPreview.getUserNumber(), statusPreview.getNumberOfStatus(), statusPreview.getUserName(), downloadURl, statusPreview.getLastStatusTime().toString());
            statusPreviewResponses.add(statusPreviewResponse);
        });

        return new ResponseEntity<>(statusPreviewResponses, HttpStatus.OK);
    }

    @GetMapping("/{userNumber}")
    public ResponseEntity<List<StatusPreviewResponse>> getOneUser(@PathVariable int userNumber) {
        log.info("IN GET ONE USER CONTROLLER NUMBER IS: " + userNumber);
        List<StatusPreviewResponse> statusPreviewResponses = new ArrayList<>();
        StatusPreview statusPreview = statusPreviewService.getOneStatusPreview(userNumber);
        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/view/")
                .path(Integer.toString(statusPreview.getUserNumber()))
                .toUriString();

        StatusPreviewResponse statusPreviewResponse = new StatusPreviewResponse(statusPreview.getUserNumber(), statusPreview.getNumberOfStatus(), statusPreview.getUserName(), downloadURl, statusPreview.getLastStatusTime().toString());
        statusPreviewResponses.add(statusPreviewResponse);

        return new ResponseEntity<>(statusPreviewResponses, HttpStatus.OK);
    }

    @GetMapping("/permitted_status_preview/{userNumber}")
    public ResponseEntity<List<StatusPreviewResponse>> getPermittedStatusPreviewsForUser(@PathVariable int userNumber){
        List<StatusPreview> statusPreviews = statusPreviewService.getPermittedStatusPreviewsForUser(userNumber);
        List<StatusPreviewResponse> statusPreviewResponses = new ArrayList<>();
        statusPreviews.forEach(statusPreview -> {
            String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/user/view/")
                    .path(Integer.toString(statusPreview.getUserNumber()))
                    .toUriString();

            StatusPreviewResponse statusPreviewResponse = new StatusPreviewResponse(statusPreview.getUserNumber(), statusPreview.getNumberOfStatus(), statusPreview.getUserName(), downloadURl, statusPreview.getLastStatusTime().toString());
            statusPreviewResponses.add(statusPreviewResponse);
        });

        return new ResponseEntity<>(statusPreviewResponses, HttpStatus.OK);
    }
}
