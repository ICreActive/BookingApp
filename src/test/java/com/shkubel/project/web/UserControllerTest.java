package com.shkubel.project.web;

import com.shkubel.project.dao.UserRepository;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private UserRepository repository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Spy
    private Invoice inv;

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

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @WithMockUser("spring")
    void getProfile() throws Exception {
        mockMvc.perform(get
                ("/users/myprofile")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("users/profile-id"));
    }

    @Test
    @WithMockUser("spring")
    void userEdit() throws Exception {
        mockMvc.perform(get
                ("/users/myprofile/{id}/edit", 1L)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("message"));
    }

    @Test
    void userUpd() throws Exception {
        doReturn(Optional.of(user))
                .when(repository)
                .findById(user.getId());
        mockMvc.perform(post("/users/myprofile/{id}/edit", user.getId())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/myprofile"));
    }

    @Test
    void showSingleInvoice() throws Exception {

        inv = Mockito.spy(new Invoice());

        doReturn(inv)
                .when(invoiceService)
                .findInvoiceById(1L);

        doReturn(32)
                .when(inv)
                .getPeriod();

        mockMvc.perform(get("/users/myprofile/invoices/{id}", 1L)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("invoice/new"))
                .andExpect(model().attribute("invoice", inv))
                .andExpect(model().attribute("period", 32));
    }
}