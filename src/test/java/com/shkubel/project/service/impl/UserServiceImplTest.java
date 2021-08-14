package com.shkubel.project.service.impl;

import com.shkubel.project.exception.UnuniqueUserException;
import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.dao.UserRepository;
import com.shkubel.project.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("Yuri");
        user.setEmail("email@email.com");
        user.setPassword("123");
        user.setId(1L);
    }

    @Test
    void findUserByIdTest() throws UserNotFoundException {
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(user.getId());
        userService.findUserById(user.getId());
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }

    @Test
    void findUserByIdThrowUserNotFoundExceptionTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserById(user.getId()));
    }

    @Test
    void findUserByEmailTest() throws UserNotFoundException {
        Mockito.doReturn(new User())
                .when(userRepository)
                .findUserByEmail(user.getEmail());
        userService.findUserByEmail(user.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).findUserByEmail(user.getEmail());
    }

    @Test
    void findUserByEmailThrowUserNotFoundExceptionTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(user.getEmail()));
    }

    @Test
    void findAllUsersTest() {
        userService.allUsers();
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void savedUserTest() throws UnuniqueUserException {

        boolean userSaved = userService.saveUser(user);
        Assertions.assertTrue(userSaved);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void savedUserStatusFalseTest() throws UnuniqueUserException {
        userService.saveUser(user);
        Assertions.assertFalse(user.isUserActive());
    }

    @Test
    void savedUserHasRoleUserTest() throws UnuniqueUserException {
        userService.saveUser(user);
        Assertions.assertEquals(user.getRoles().toString(), "[ROLE_USER]");
    }

    @Test
    void savedUserHasSingleRoleTest() throws UnuniqueUserException {
        userService.saveUser(user);
        Assertions.assertEquals(user.getRoles().size(), 1);

    }

    @Test
    void deleteUserReturnTrueTest() throws UserNotFoundException {
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(user.getId());
        boolean userDeleted = userService.deleteUser(user.getId());
        Assertions.assertTrue(userDeleted);
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }

    @Test
    void deleteUserThrowUserNotFoundExceptionTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId()));
    }

    @Test
    void deleteUserSetUserStatusActiveFalseTest() throws UserNotFoundException {
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(user.getId());
        userService.deleteUser(user.getId());
        Assertions.assertFalse(user.isUserActive());
    }

    @Test
    void updateUserThrowUserNotFoundExceptionTest() {
        Long id = 10L;
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(id, user));
    }

    @Test
    void updateUserTest() throws UserNotFoundException {
        Long id = 1L;
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(user.getId());
        userService.updateUser(id, user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }


    @Test
    void findUserByStatusActiveTest() {
        userService.findUsersByStatusActive();
        Mockito.verify(userRepository, Mockito.times(1)).findUsersByUserActive();
    }


    @Test
    void restoreUserThrowsUserNotFoundExceptionTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.restoreUser(user.getId()));
    }

    @Test
    void restoreUserSetActiveTrueTest() throws UserNotFoundException {
        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(user.getId());
        userService.restoreUser(user.getId());
        Assertions.assertTrue(user.isUserActive());
    }

    @Test
    void activateUserThrowUserNotFoundExceptionTest() {
        String code = "111";
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.activateUser(code));
    }

    @Test
    void activateUserSetActivationCodeNull() throws UserNotFoundException {
        String code = "111";
        user.setActivationCode(code);
        Mockito.doReturn(user)
                .when(userRepository)
                .findUserByActivationCode(code);
        userService.activateUser(code);
        Assertions.assertNull(user.getActivationCode());
    }

    @Test
    void activateUserSaveTest() throws UserNotFoundException {
        String code = "111";
        Mockito.doReturn(user)
                .when(userRepository)
                .findUserByActivationCode(code);
        userService.activateUser(code);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void activateUserReturnTrueTest() throws UserNotFoundException {
        String code = "111";
        Mockito.doReturn(user)
                .when(userRepository)
                .findUserByActivationCode(code);
        Assertions.assertTrue(userService.activateUser(code));
    }

    @Test
    void updateResetPasswordTokenTest() throws UserNotFoundException {
        String token = "111";
        Mockito.doReturn(user)
                .when(userRepository)
                .findUserByEmail(user.getEmail());
        userService.updateResetPasswordToken(token, user.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).findUserByEmail(user.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void updateResetPasswordTokenThrowUserNotFoundExceptionTest() {
        String token = "111";
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateResetPasswordToken(token, user.getEmail()));
    }

    @Test
    void updateResetPasswordTokenSetTokenTest() throws UserNotFoundException {
        String token = "111";
        Mockito.doReturn(user)
                .when(userRepository)
                .findUserByEmail(user.getEmail());
        userService.updateResetPasswordToken(token, user.getEmail());
        Assertions.assertEquals(token, user.getResetPasswordToken());
    }


    @Test
    void getByResetPasswordTokenTest() {
        String token = "123";
        userService.getByResetPasswordToken(token);
        Mockito.verify(userRepository, Mockito.times(1)).findUserByResetPasswordToken(token);
    }


    @Test
    void updatePasswordTest() {
        user.setResetPasswordToken("111");
        userService.updatePassword(user, "654321");
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assertions.assertNull(user.getResetPasswordToken());
    }

    @Test
    void findUserByUserNameThrowUserNotFoundExceptionTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserByUserName(user.getUsername()));
    }

    @Test
    void findUserByUserNameTest() throws UserNotFoundException {
        Mockito.doReturn(new User())
                .when(userRepository)
                .findUserByUsername("Yuri");
        User userInDb = userService.findUserByUserName(user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(user.getUsername());
        Assertions.assertNotNull(userInDb);
    }

    @Test
    void findAdminsTest() {
        userService.findAdmins();
        Mockito.verify(userRepository, Mockito.times(1)).findAdmins();
    }

    @Test
    void userReceivedActivationCodeOnSaveTest() throws UnuniqueUserException {
        userService.saveUser(user);
        Assertions.assertNotNull(user.getActivationCode());
    }


}