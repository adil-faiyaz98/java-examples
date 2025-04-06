package com.example.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

/**
 * Service demonstrating different method-level security annotations.
 */
@Service
public class SecuredService {

    /**
     * Method secured with @PreAuthorize.
     * This checks the authorization before the method is executed.
     *
     * @param id the ID to check
     * @return a message
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public String preAuthorizedMethod(Long id) {
        return "You have access to resource with ID: " + id;
    }

    /**
     * Method secured with @PostAuthorize.
     * This checks the authorization after the method is executed, and can use the return value.
     *
     * @param username the username to check
     * @return a message
     */
    @PostAuthorize("returnObject.contains(authentication.name)")
    public String postAuthorizedMethod(String username) {
        return "Data for user: " + username;
    }

    /**
     * Method secured with @Secured.
     * This is the older Spring Security annotation for method security.
     *
     * @return a message
     */
    @Secured({"ROLE_ADMIN"})
    public String securedMethod() {
        return "This is a secured method for admins only";
    }

    /**
     * Method secured with @RolesAllowed.
     * This is the JSR-250 standard annotation for method security.
     *
     * @return a message
     */
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public String rolesAllowedMethod() {
        return "This method is allowed for users and admins";
    }

    /**
     * Method with a complex SpEL expression in @PreAuthorize.
     * This demonstrates more advanced authorization rules.
     *
     * @param id the ID to check
     * @param owner the owner username
     * @return a message
     */
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #owner == authentication.name)")
    public String ownershipCheck(Long id, String owner) {
        return "You have access to resource " + id + " owned by " + owner;
    }
}
