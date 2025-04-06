package com.example.aop.controller;

import com.example.aop.annotation.RequiresRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for demonstrating security aspects.
 */
@RestController
@RequestMapping("/api/secured")
public class SecuredController {

    /**
     * Public endpoint that anyone can access.
     */
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint that anyone can access.");
    }

    /**
     * User endpoint that requires USER role.
     */
    @GetMapping("/user")
    @RequiresRole({"USER", "ADMIN"})
    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("This endpoint requires USER role.");
    }

    /**
     * Admin endpoint that requires ADMIN role.
     */
    @GetMapping("/admin")
    @RequiresRole({"ADMIN"})
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("This endpoint requires ADMIN role.");
    }

    /**
     * Manager endpoint that requires MANAGER role.
     * 
     * This will fail because our SecurityService only simulates ADMIN role.
     */
    @GetMapping("/manager")
    @RequiresRole({"MANAGER"})
    public ResponseEntity<String> managerEndpoint() {
        return ResponseEntity.ok("This endpoint requires MANAGER role.");
    }
}
