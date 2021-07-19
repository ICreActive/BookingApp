package com.shkubel.project.service.impl;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.repo.RoleRepository;
import com.shkubel.project.models.repo.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private RoleRepository roleRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("Yuri");
        user.setEmail("email@email.com");
        user.setPassword("123456");
    }

    @Test
    void savedUserTest() {

        boolean userSaved = userService.saveUser(user);
        Assertions.assertTrue(userSaved);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void savedUserStatusFalseTest() {
        userService.saveUser(user);
        Assertions.assertFalse(user.isUserActive());
    }

    @Test
    void savedUserHasRoleUserTest() {
        userService.saveUser(user);
        Assertions.assertEquals(user.getRoles().toString(), "[ROLE_USER]");
    }
    @Test
    void savedUserHasSingleRoleTest() {
        userService.saveUser(user);
        Assertions.assertEquals(user.getRoles().size(), 1);
    }





}