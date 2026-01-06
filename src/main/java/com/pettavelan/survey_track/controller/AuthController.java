package com.pettavelan.survey_track.controller;

import com.pettavelan.survey_track.dto.*;
import com.pettavelan.survey_track.entity.User;
import com.pettavelan.survey_track.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest request) {
        User savedUser = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegistrationResponse(
                        savedUser.getEmail(),
                        "User registered successfully",
                        "SUCCESS"
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthData authData = userService.login(request);
        setRefreshTokenCookie(response, authData.refreshToken(), 7 * 24 * 60 * 60);

        return ResponseEntity.ok(new LoginResponse(
                authData.accessToken(),
                authData.email(),
                authData.role(),
                "SUCCESS"
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@CookieValue(name = "refreshToken") String refreshToken) {
        AuthData data = userService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(new LoginResponse(
                data.accessToken(),
                data.email(),
                data.role(),
                "SUCCESS"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null) {
            userService.logout(refreshToken);
        }

        setRefreshTokenCookie(response, "", 0);
        return ResponseEntity.noContent().build();
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String token, int maxAge) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(false) // Toggle to true in production (HTTPS)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
