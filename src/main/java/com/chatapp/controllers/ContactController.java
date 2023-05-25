package com.chatapp.controllers;

import com.chatapp.models.Contact;
import com.chatapp.security.ContactSecurity;
import com.chatapp.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.chatapp.services.ContactService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserSecurity userSecurity;

    @Autowired
    private ContactSecurity contactSecurity;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Contact> getContacts() {
        return contactService.getContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable int id) {
        if (userSecurity.isAdmin() || contactSecurity.isContactOwner(id)) {
            if (contactService.getContactById(id).isPresent()) {
                return ResponseEntity.ok().body(contactService.getContactById(id));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserContacts(@PathVariable int id) {
        if (userSecurity.isAdmin() || userSecurity.isCurrentUser(id)) {
            return ResponseEntity.ok().body(contactService.getUserContacts(id));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        if (userSecurity.isAdmin() || userSecurity.isCurrentUser(contact.getUserId())) {
            Contact newContact = contactService.addContact(contact);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newContact.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newContact);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Contact> renameContact(@RequestBody String newName, @PathVariable int id) {
        if (userSecurity.isAdmin() || contactSecurity.isContactOwner(id)) {
            if (contactService.getContactById(id).isPresent()) {
                Contact newContact = contactService.renameContact(id, newName);
                return ResponseEntity.ok().body(newContact);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Contact> setContactDeleted(@PathVariable int id) {
        if (userSecurity.isAdmin() || contactSecurity.isContactOwner(id)) {
            if (contactService.getContactById(id).isPresent()) {
                contactService.setContactDeleted(id);
                return ResponseEntity.ok().body(contactService.getContactById(id).get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteContact(@PathVariable int id) {
        if (contactService.getContactById(id).isPresent()) {
            contactService.setContactDeleted(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
