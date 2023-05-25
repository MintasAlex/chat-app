package com.chatapp.controllers;

import com.chatapp.models.UserAccount;
import com.chatapp.security.UserSecurity;
import com.chatapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserSecurity userSecurity;

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAccount> getUserAccounts() {
        return userAccountService.getUserAccounts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    public ResponseEntity<?> getUserAccountById(@PathVariable int id) {
        if (userAccountService.getUserAccountById(id).isPresent()) {
            return ResponseEntity.ok(userAccountService.getUserAccountById(id).get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#id)")
    public ResponseEntity<?> updateUserAccount(@Valid @RequestBody UserAccount newUserAccount, @PathVariable int id) {
        if (userAccountService.updateUserAccount(newUserAccount, id).isPresent()) {
            return ResponseEntity.ok().body(userAccountService.updateUserAccount(newUserAccount, id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> setUserAccountInactive(@PathVariable int id) {
        if (userSecurity.isAdmin() || userSecurity.isCurrentUser(id)) {
            if (userAccountService.getUserAccountById(id).isPresent()) {
                return ResponseEntity.ok().body(userAccountService.setUserAccountInactive(id));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserAccount(@PathVariable int id) {
        if (userAccountService.deleteUserAccount(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
