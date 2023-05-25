package com.chatapp.repositories;

import com.chatapp.models.UserRole;
import com.chatapp.models.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(Long userId);

    Optional<UserRole> findByUserIdAndRoleId(Long userId, int roleId);

    Boolean existsByUserIdAndRoleId(Long userId, int roleId);

    void deleteByUserIdAndRoleId(Long userId, int roleId);
}
