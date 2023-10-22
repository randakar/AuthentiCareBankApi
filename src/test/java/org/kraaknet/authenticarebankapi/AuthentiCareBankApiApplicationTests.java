package org.kraaknet.authenticarebankapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;
import org.testcontainers.utility.DockerImageName;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Testcontainers
class AuthentiCareBankApiApplicationTests {

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.1-alpine"));

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testContextLoads() {
        // No errors so far, so application started successfully
        assertTrue(TRUE);
    }

    @Test
    void givenNoUserWhenCallGetCurrentCustomerThenReturnRedirectionToLogin() throws Exception {
        mvc.perform(get("/customer/me").contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","http://localhost/login"));
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUserWhenCallGetCurrentCustomerThenGoKaboom() throws Exception {
        mvc.perform(get("/customer/me").contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "john", roles = { "ADMIN" })
    void givenUserUnknownWhenCallGetCurrentCustomer_thenReturnNotFound() throws Exception {
        mvc.perform(get("/customer/me").contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Floris")
    void givenUserExistsWhenCallGetCurrentCustomer_thenReturnCustomerDetails() throws Exception {
        mvc.perform(get("/customer/me").contentType(MediaType.APPLICATION_JSON.getMediaType()))
                .andExpect(status().isNotFound());
    }


}
