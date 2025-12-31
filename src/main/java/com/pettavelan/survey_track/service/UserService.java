//package com.pettavelan.survey_track.service;
//
//import com.pettavelan.survey_track.entity.User;
//import com.pettavelan.survey_track.enums.Role;
//import com.pettavelan.survey_track.enums.UserStatus;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserService {
//
//    User registerUser(User user);
//    Optional<User> login(String email, String password);
//
//    User updateProfile(Long userId, User userDetails);
//    User getUserById(Long userId);
//
//    List<User> getAllUsers();
//    List<User> getUsersByRole(Role role);
//
//    void changeUserStatus(Long userId, UserStatus status);
//}
