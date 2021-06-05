package com.shkubel.project.repo;

import com.shkubel.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

User findUsersByUsername(String username);

}
