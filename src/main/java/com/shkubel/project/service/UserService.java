package com.shkubel.project.service;

import com.shkubel.project.models.entity.User;

import java.util.List;

public interface UserService {

    User findUserById (Long userId);
    List<User> allUsers ();
    boolean saveUser (User user);
    boolean deleteUser (Long userId);
    boolean updateUser (Long userId, User user);
}
