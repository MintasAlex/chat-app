package com.chatapp.security;

import com.chatapp.security.services.UserDetailsImpl;
import com.chatapp.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("contactSecurity")
public class ContactSecurity {

    @Autowired
    private ContactService contactService;

    public Boolean isContactOwner(int contactId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (contactService.getContactById(contactId).isPresent())
            return contactService.getContactById(contactId).get().getUserId() == userDetails.getId();
        else
            return false;
    }
}
