package com.shkubel.project.web;

import com.shkubel.project.config.CustomAuthProvider;
import com.shkubel.project.config.WebSecurityConfig;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private CustomAuthProvider customAuthProvider;
//
//    @MockBean
//    private WebSecurityConfig webSecurityConfig;
//
//    @MockBean
//    private MailSender mailSender;

    @BeforeEach
    void setUp() {

    }

    @Test
    void newUser() throws Exception {
        mockMvc.perform(get("/users/new" )).andExpect((status().isOk()));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users/new" )).andExpect((status().isOk()));
    }

    @Test
    void activate() throws Exception {
        mockMvc.perform(get("/users/new" )).andExpect((status().isOk()));

    }
}