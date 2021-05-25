package com.shkubel.project.repo;

import com.shkubel.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

User findUsersByUsername(String username);

}
