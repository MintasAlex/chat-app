package com.chatapp.security;

import com.chatapp.models.UserAccount;
import com.chatapp.repositories.UserAccountRepository;
import com.chatapp.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("userSecurity")
public class UserSecurity {

        @Autowired
        private UserAccountRepository userAccountRepository;

        public boolean isCurrentUser(int userId){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return Objects.equals(userDetails.getId(), userId);
        }

        public boolean isAdmin(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        }

}
