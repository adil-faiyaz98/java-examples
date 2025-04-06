package com.example.security.config;

import com.example.security.model.ERole;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Component to initialize sample data on application startup.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initialize roles and sample users on application startup.
     *
     * @param args command line arguments
     */
    @Override
    public void run(String... args) {
        // Initialize roles if they don't exist
        initRoles();
        
        // Create sample users if they don't exist
        createSampleUsers();
    }

    /**
     * Initialize roles.
     */
    private void initRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }

    /**
     * Create sample users with different roles.
     */
    private void createSampleUsers() {
        if (userRepository.count() == 0) {
            // Create a regular user
            createUser("user", "user@example.com", "password", Set.of(ERole.ROLE_USER));
            
            // Create a moderator
            createUser("mod", "mod@example.com", "password", Set.of(ERole.ROLE_USER, ERole.ROLE_MODERATOR));
            
            // Create an admin
            createUser("admin", "admin@example.com", "password", Set.of(ERole.ROLE_USER, ERole.ROLE_ADMIN));
        }
    }

    /**
     * Helper method to create a user with specified roles.
     *
     * @param username the username
     * @param email the email
     * @param password the password
     * @param roleNames the roles to assign
     */
    private void createUser(String username, String email, String password, Set<ERole> roleNames) {
        User user = new User(username, email, passwordEncoder.encode(password));
        
        Set<Role> roles = new HashSet<>();
        for (ERole roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(role);
        }
        
        user.setRoles(roles);
        userRepository.save(user);
    }
}
