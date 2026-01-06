package com.pettavelan.survey_track.service;

import com.pettavelan.survey_track.dto.AuthData;
import com.pettavelan.survey_track.dto.LoginRequest;
import com.pettavelan.survey_track.dto.RegistrationRequest;
import com.pettavelan.survey_track.entity.User;
import com.pettavelan.survey_track.enums.Role;
import com.pettavelan.survey_track.enums.UserStatus;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(RegistrationRequest request);
    AuthData login(LoginRequest request);

    User updateProfile(Long userId, User userDetails);
    User getUserById(Long userId);

    List<User> getAllUsers();
    List<User> getUsersByRole(Role role);

    AuthData refreshAccessToken(String refreshTokenRequest);
    void changeUserStatus(Long userId, UserStatus status);

    @Transactional
    void logout(String refreshToken);

    @Transactional
    void logoutAllSessions(User user);
}
