package com.shkubel.project.service;

import com.shkubel.project.exception.UserNotFoundException;
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

    boolean restoreUser(Long userId) throws UserNotFoundException;

    List<User> findAdmins ();

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String password);

    boolean activateUser(String code);

    User findUserByUserName (String name) throws UserNotFoundException;
}
