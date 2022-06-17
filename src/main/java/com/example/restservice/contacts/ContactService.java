package com.example.restservice.contacts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.restservice.exceptions.BadRequestException;
import com.example.restservice.users.UserRepository;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ContactService implements ContactInterface {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Contact> getAllUsersAndTheirContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact addUserOneContact(Contact contact) {
        int userNumber = contact.getUserNumber();
        int usersContact = contact.getUsersContact();
            if (!(userRepository.findById(userNumber).isPresent())) throw new BadRequestException("The user with number: " + userNumber + " does not have an account on our platform.");

            if (!(userRepository.findById(usersContact).isPresent())) throw new BadRequestException("The user with number: " + usersContact + " does not have an account on our platform.");
            if (contact.getGivenName() == null || contact.getGivenName().trim().length() < 3) throw new BadRequestException("Your contact must have a name of atleast 3 characters");
            return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContactsOfAUser(int userNumber) {
        return contactRepository.findAllByUserNumber(userNumber);
    }
}
