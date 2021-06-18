package com.shkubel.project.service;

import com.shkubel.project.models.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {

    User findUserById (Long userId);
    List <User> findUserByEmail(String email);
    List<User> allUsers ();
    boolean saveUser (User user);
    boolean deleteUser (Long userId);
    boolean updateUser (Long userId, User user);
    List <User> findUsersByUserActive(@NotNull boolean userActive);
}
