package com.example.security.repository;

import com.example.security.model.ERole;
import com.example.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find a role by name.
     *
     * @param name the role name to search for
     * @return an Optional containing the role if found
     */
    Optional<Role> findByName(ERole name);
}
