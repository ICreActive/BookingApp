package com.shkubel.project.service;

import com.shkubel.project.exception.UnuniqueUserException;
import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.oidc.CustomOidUser;

import java.util.List;

public interface UserService {

    User findUserById(Long userId) throws UserNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;

    List<User> allUsers();

    boolean saveUser(User user) throws UnuniqueUserException;

    boolean deleteUser(Long userId) throws UserNotFoundException;

    void updateUser(Long userId, User user) throws UserNotFoundException;

    List<User> findUsersByStatusActive ();

    void restoreUser(Long userId) throws UserNotFoundException;

    List<User> findAdmins ();

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String password);

    boolean activateUser(String code) throws UserNotFoundException;

    User findUserByUserName (String name) throws UserNotFoundException;

    void processOAuthPostLogin (CustomOidUser oidUser);

}
