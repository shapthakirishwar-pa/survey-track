//package com.pettavelan.survey_track.entity;
//
//import com.pettavelan.survey_track.enums.Role;
//import com.pettavelan.survey_track.enums.UserStatus;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//
//@Entity
//@Table(name = "users")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    private String name;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    @Column(unique = true)
//    @NotBlank(message = "Email is required")
//    @Email(message = "Please provide a valid email address")
//    private String email;
//
//    private String department;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserStatus status = UserStatus.ACTIVE;
//}
