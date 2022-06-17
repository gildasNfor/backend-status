package com.example.restservice.contacts;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactInterface {
    public List<Contact> getAllUsersAndTheirContacts();
    public Contact addUserOneContact(Contact contact);
    public List<Contact> getAllContactsOfAUser(int userNumber);
}
