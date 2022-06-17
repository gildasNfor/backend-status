package com.example.restservice.statusPreview;

import com.example.restservice.contacts.Contact;
import com.example.restservice.contacts.ContactRepository;
import com.example.restservice.exceptions.BadRequestException;
import com.example.restservice.exceptions.NotFoundException;
import com.example.restservice.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class StatusPreviewService implements StatusPreviewInterface {

    @Autowired
    private StatusPreviewRepository statusPreviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public StatusPreview deleteStatusPreview(int userNumber) {
        return null;
    }

    @Override
    public List<StatusPreview> getAllStatusPreviews() {
        return statusPreviewRepository.findAll();
    }

    @Override
    public StatusPreview getOneStatusPreview(int userNumber) {
        if(!(userNumber > 0)) throw new BadRequestException("A userNumber form data field is required");
        if(userNumber < 600000000 || userNumber > 700000000) throw new BadRequestException("The userNumber is not a valid phone number");
        if(!statusPreviewRepository.findById(userNumber).isPresent()) throw new NotFoundException("The user with that Number does not have a status preview");
        return statusPreviewRepository.findById(userNumber).get();
    }

    @Override
    public List<StatusPreview> getPermittedStatusPreviewsForUser(int userNumber) {
        if(!(userNumber > 0)) throw new BadRequestException("A userNumber form data field is required");
        if(userNumber < 600000000 || userNumber > 700000000) throw new BadRequestException("The userNumber is not a valid phone number");
        if(!userRepository.findById(userNumber).isPresent()) throw new NotFoundException("The user with that Number is not registered in our platform");
        List<Contact> usersContacts = contactRepository.findAllByUserNumber(userNumber);
        List<Contact> validContacts = new ArrayList<>();
        usersContacts.forEach(usersContact -> {
            List<Contact> correspondingContacts = contactRepository.findAllByUserNumber(usersContact.getUsersContact());
            correspondingContacts.forEach(contact -> {
                if(contact.getUsersContact() == userNumber) validContacts.add(contact);
            });
        });
        List<StatusPreview> validStatusPreviews = new ArrayList<>();
        validContacts.forEach(contact -> {
            Optional<StatusPreview> optionalStatusPreview = statusPreviewRepository.findById(contact.getUserNumber());
            if(optionalStatusPreview.isPresent()) validStatusPreviews.add(optionalStatusPreview.get());
        });
        return validStatusPreviews;
    }
}
