package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    @Query("select u from User u where u.isUserActive=true")
    List<User> findUsersByUserActive();

    User findUserByActivationCode(String code);

}
