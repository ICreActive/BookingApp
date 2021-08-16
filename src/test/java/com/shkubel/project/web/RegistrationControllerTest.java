package com.shkubel.project.web;

import com.shkubel.project.dao.UserRepository;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository repository;


    private User user;


    @BeforeEach
    void setUp() {
        String var = "Variable";
        user = new User();
        user.setUsername(var);
        user.setUserFirstname(var);
        user.setUserLastname(var);
        user.setAddress(var);
        user.setEmail(var);
        user.setId(1L);
        user.setActivationCode("123");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }


    @Test
    void newUser() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andExpect((status().isOk()))
                .andExpect(view().name("users/new"))
                .andExpect(model().attributeExists("userNew"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users/new"))
                .andExpect((status().isOk()))
                .andExpect(view().name("/users/new"))
                .andExpect(model().attributeExists("userNew"));

    }

    @Test
    @WithMockUser("spring")
    void activate() throws Exception {

        String code = "code";

        Mockito.doReturn(user)
                .when(repository)
                .findUserByActivationCode(code);

        mockMvc.perform(get("/users/activate/{code}", code))
                .andExpect((status().isOk()));
    }
}