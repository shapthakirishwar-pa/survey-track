package com.pettavelan.survey_track.controller;

import com.pettavelan.survey_track.dto.AuthRegistrationRequest;
import com.pettavelan.survey_track.dto.AuthResponse;
import com.pettavelan.survey_track.entity.User;
import com.pettavelan.survey_track.enums.Role;
import com.pettavelan.survey_track.enums.UserStatus;
import com.pettavelan.survey_track.repository.UserRepository;
import com.pettavelan.survey_track.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRegistrationRequest request) {
        try {
            User savedUser = userService.registerUser(request);
            return ResponseEntity.ok(new AuthResponse(
                            "Registration successful",
                            "SUCCESS",
                            savedUser.getEmail()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(
                            e.getMessage(),
                            "FAIL",
                            request.email())
                    );
        }
    }
}
