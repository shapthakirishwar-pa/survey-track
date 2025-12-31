//package com.pettavelan.survey_track.service.impl;
//
//import com.pettavelan.survey_track.entity.User;
//import com.pettavelan.survey_track.enums.Role;
//import com.pettavelan.survey_track.enums.UserStatus;
//import com.pettavelan.survey_track.repository.UserRepository;
//import com.pettavelan.survey_track.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public User registerUser(User user) {
//        return null;
//    }
//
//    @Override
//    public Optional<User> login(String email, String password) {
//        return Optional.empty();
//    }
//
//    @Override
//    public User updateProfile(Long userId, User userDetails) {
//        return null;
//    }
//
//    @Override
//    public User getUserById(Long userId) {
//        return null;
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        return List.of();
//    }
//
//    @Override
//    public List<User> getUsersByRole(Role role) {
//        return List.of();
//    }
//
//    @Override
//    public void changeUserStatus(Long userId, UserStatus status) {
//
//    }
//}
