package com.pettavelan.survey_track.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public Map<String, String> publicAccess() {
        return Map.of("message", "Public Content: No login required.");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public Map<String, String> userAccess() {
        return Map.of("message", "User Content: Access granted for any logged-in user.");
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public Map<String, String> managerAccess() {
        return Map.of("message", "Manager Content: Access restricted to Managers only.");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> adminAccess() {
        return Map.of("message", "Admin Board: Restricted to Administrators.");
    }

    @GetMapping("/debug")
    public Map<String, Object> debug(Authentication authentication) {
        return Map.of(
                "name", authentication.getName(),
                "authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                "principal", authentication.getPrincipal()
        );
    }
}
