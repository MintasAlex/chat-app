package com.chatapp.services;

import com.chatapp.models.Contact;
import com.chatapp.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> getUserContacts(int userId) {
        return contactRepository.findByUserId(userId);
    }

    public Optional<Contact> getContactById(int id) {
        return contactRepository.findById(id);
    }

    public Contact addContact(Contact contact) {
        Contact newContact = new Contact(contact.getUserId(), contact.getContactId(), contact.getDisplayName());
        return contactRepository.save(newContact);
    }

    public Contact renameContact(int id, String name) {
        if (contactRepository.existsById(id)) {
            Contact contact = contactRepository.findById(id).get();
            contact.setDisplayName(name);
            return contactRepository.save(contact);
        } else {
            return null;
        }
    }

    public Contact setContactDeleted(int id) {
        if (contactRepository.existsById(id)) {
            Contact contact = contactRepository.findById(id).get();
            contact.setIsDeleted(true);
            return contactRepository.save(contact);
        } else {
            return null;
        }
    }


}
