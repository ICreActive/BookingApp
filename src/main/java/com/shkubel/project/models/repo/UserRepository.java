package com.shkubel.project.models.repo;

import com.shkubel.project.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

User findUserByUsername(String username);

}
