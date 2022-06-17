package com.example.restservice.contacts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Component
@Slf4j
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllUsersAndTheirContacts(){
        return new ResponseEntity<>(contactService.getAllUsersAndTheirContacts(), HttpStatus.OK);
    }

    @GetMapping("/{userNumber}")
    public ResponseEntity<List<Contact>> getContactsOfAUser(@PathVariable int userNumber){
        return new ResponseEntity<>(contactService.getAllContactsOfAUser(userNumber), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Contact> addUserOneContact(@Valid @RequestBody Contact contact){
        return new ResponseEntity<>(contactService.addUserOneContact(contact), HttpStatus.CREATED);
    }

}
