package com.pettavelan.survey_track.service.impl;

import com.pettavelan.survey_track.dto.AuthRegistrationRequest;
import com.pettavelan.survey_track.dto.AuthResponse;
import com.pettavelan.survey_track.entity.User;
import com.pettavelan.survey_track.enums.Department;
import com.pettavelan.survey_track.enums.Role;
import com.pettavelan.survey_track.enums.UserStatus;
import com.pettavelan.survey_track.repository.UserRepository;
import com.pettavelan.survey_track.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(AuthRegistrationRequest request) {
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
    public Optional<User> login(String email, String password) {
        return Optional.empty();
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

    }
}
