package com.pettavelan.survey_track.service.impl;

import com.pettavelan.survey_track.dto.AuthData;
import com.pettavelan.survey_track.dto.LoginRequest;
import com.pettavelan.survey_track.dto.RegistrationRequest;
import com.pettavelan.survey_track.entity.RefreshToken;
import com.pettavelan.survey_track.entity.User;
import com.pettavelan.survey_track.enums.Department;
import com.pettavelan.survey_track.enums.Role;
import com.pettavelan.survey_track.enums.UserStatus;
import com.pettavelan.survey_track.repository.RefreshTokenRepository;
import com.pettavelan.survey_track.repository.UserRepository;
import com.pettavelan.survey_track.security.JwtService;
import com.pettavelan.survey_track.security.SecurityUser;
import com.pettavelan.survey_track.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public User register(RegistrationRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Error: Email is already registered!");
        }

        Department department = Department.valueOf(request.department().toUpperCase());

        Role assignedRole = (request.role() != null)
                ? Role.valueOf(request.role().toUpperCase())
                : Role.EMPLOYEE;

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .department(department)
                .role(assignedRole)
                .build();

        return userRepository.save(user);
    }

    @Override
    public AuthData login(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                ));

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        assert securityUser != null;
        User user = securityUser.getUser();

        String accesToken = jwtService.generateToken(securityUser);
        String refreshToken = createRefreshToken(user);

        saveRefreshTokenToDb(user, refreshToken);

        return new AuthData(
                accesToken,
                refreshToken,
                user.getEmail(),
                user.getRole().name()
        );
    }

    @Transactional
    private String createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS)) // 7-day expiry
                .build();

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Transactional
    private void saveRefreshTokenToDb(User user, String tokenString) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(tokenString);

        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshTokenRepository.save(refreshToken);
    }

    public AuthData refreshAccessToken(String refreshTokenRequest) {
        return refreshTokenRepository.findByToken(refreshTokenRequest)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtService.generateToken(new SecurityUser(user));

                    return new AuthData(
                            newAccessToken,
                            refreshTokenRequest, // Keep the same refresh token
                            user.getEmail(),
                            user.getRole().name()
                    );
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found! Please log in again."));
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please log in again.");
        }
        return token;
    }

    @Override
    public User updateProfile(Long userId, User userDetails) {
        return null;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return List.of();
    }

    @Override
    public void changeUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setStatus(status);
        userRepository.save(user);

        logoutAllSessions(user);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(refreshTokenRepository::delete);
    }

    @Transactional
    public void logoutAllSessions(User user) {
        // Logout from all devices or Password Resets
        refreshTokenRepository.deleteByUser(user);
    }
}
