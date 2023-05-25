package com.chatapp.services;

import com.chatapp.models.UserRole;
import com.chatapp.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getUserRoles() {
        return userRoleRepository.findAll();
    }

    public List<UserRole> getUserRolesByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }

    public Optional<UserRole> getUserRoleByUserIdAndRoleId(Long userId, int roleId) {
        return userRoleRepository.findByUserIdAndRoleId(userId, roleId);
    }

    public UserRole addUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public Boolean deleteUserRole(Long userId, int roleId) {
        if (userRoleRepository.existsByUserIdAndRoleId(userId, roleId)) {
            userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
            return true;
        } else {
            return false;
        }
    }

}
