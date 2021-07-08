package com.shkubel.project.service;

import com.shkubel.project.models.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {

    User findUserById(Long userId);

    User findUserByEmail(String email);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(Long userId);

    boolean updateUser(Long userId, User user);

    List<User> findUsersByStatus(@NotNull boolean userActive);

    boolean restoreUser(Long userId);

    User findUserByUsername(String username);

}
