//package com.pettavelan.survey_track.repository;
//
//
//import com.pettavelan.survey_track.entity.User;
//import com.pettavelan.survey_track.enums.Role;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//    Boolean existsByEmail(String email);
//
//    List<User> findByDepartment(String department);
//
//    List<User> findByRole(Role role);
//}
