package com.chatapp.services;

import com.chatapp.models.UserAccount;
import com.chatapp.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<UserAccount> getUserAccounts() {
        return userAccountRepository.findAll();
    }

    public Optional<UserAccount> getUserAccountById(int id) {
        return userAccountRepository.findById(id);
    }

    public Optional<UserAccount> updateUserAccount(UserAccount newUserAccount, int id) {
        return userAccountRepository.findById(id)
                .map(userAccount -> {
                    userAccount.setUsername(newUserAccount.getUsername());
                    userAccount.setEmail(newUserAccount.getEmail());
                    userAccount.setPassword(newUserAccount.getPassword());
                    userAccount.setCreatedAt(newUserAccount.getCreatedAt());
                    userAccountRepository.save(userAccount);
                    return userAccount;
                });
    }

    public UserAccount setUserAccountInactive(int id) {
        if (userAccountRepository.existsById(id)) {
            UserAccount userAccount = userAccountRepository.findById(id).get();
            userAccount.setIsActive(false);
            return userAccountRepository.save(userAccount);
        } else {
            return null;
        }
    }

    public Boolean deleteUserAccount(int id) {
        if (userAccountRepository.existsById(id)) {
            userAccountRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
