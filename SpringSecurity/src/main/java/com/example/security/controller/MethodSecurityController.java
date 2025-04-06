package com.example.security.controller;

import com.example.security.service.SecuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller demonstrating method-level security.
 */
@RestController
@RequestMapping("/api/secured")
public class MethodSecurityController {

    @Autowired
    private SecuredService securedService;

    /**
     * Endpoint using a pre-authorized method.
     *
     * @param id the resource ID
     * @return the result from the secured service
     */
    @GetMapping("/pre-authorized/{id}")
    public String preAuthorizedEndpoint(@PathVariable Long id) {
        return securedService.preAuthorizedMethod(id);
    }

    /**
     * Endpoint using a post-authorized method.
     *
     * @param authentication the current authentication
     * @return the result from the secured service
     */
    @GetMapping("/post-authorized")
    public String postAuthorizedEndpoint(Authentication authentication) {
        return securedService.postAuthorizedMethod(authentication.getName());
    }

    /**
     * Endpoint using a method secured with @Secured.
     *
     * @return the result from the secured service
     */
    @GetMapping("/secured")
    public String securedEndpoint() {
        return securedService.securedMethod();
    }

    /**
     * Endpoint using a method secured with @RolesAllowed.
     *
     * @return the result from the secured service
     */
    @GetMapping("/roles-allowed")
    public String rolesAllowedEndpoint() {
        return securedService.rolesAllowedMethod();
    }

    /**
     * Endpoint using a method with ownership check.
     *
     * @param id the resource ID
     * @param authentication the current authentication
     * @return the result from the secured service
     */
    @GetMapping("/ownership/{id}")
    public String ownershipEndpoint(@PathVariable Long id, Authentication authentication) {
        return securedService.ownershipCheck(id, authentication.getName());
    }
}
