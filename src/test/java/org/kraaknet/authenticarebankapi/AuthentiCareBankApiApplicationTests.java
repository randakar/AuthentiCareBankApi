package org.kraaknet.authenticarebankapi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

import static java.lang.Boolean.TRUE;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.kraaknet.authenticarebankapi.AuthentiCareBankApiApplicationTests.UserRole.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Testcontainers
class AuthentiCareBankApiApplicationTests {

    private final static HttpHeaders httpHeaders = createJsonRequestHeaders();

    private static HttpHeaders createJsonRequestHeaders() {
        var requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return requestHeaders;
    }


    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.1-alpine"));


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testContextLoads() {
        // No errors so far, so application started successfully
        assertTrue(TRUE);
    }

    @Test
    void givenNoUserWhenCallGetCurrentCustomerThenReturnRedirectionToLogin() {
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Void> result = testRestTemplate
                .exchange("/customer/me", GET, entity, Void.class);
        assertNotNull(result);
        assertTrue(result.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401)));
    }

    @Test
    void givenAnonymousUserWhenCallGetCurrentCustomerThenGoKaboom() {
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Void> result = restTemplateForRole(NONE)
                .exchange("/customer/me", GET, entity, Void.class);
        assertNotNull(result);
        assertTrue(result.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401)));
    }

    @Test
    void givenUserUnknownWhenCallGetCurrentCustomerThenReturnNotFound() {
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Void> result = restTemplateForRole(USER)
                .exchange("/customer/me", GET, entity, Void.class);
        assertNotNull(result);
        assertTrue(result.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404)));
    }

    @Test
    void givenUserExistsWhenCallGetCurrentCustomerThenReturnCustomerDetails() {
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Void> result = restTemplateForRole(USER)
                .exchange("/customer/me", GET, entity, Void.class);
        assertNotNull(result);
        assertTrue(result.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404)));
    }

    @Test
    void givenUserExistsAndUserCustomerWhenCallGetCustomerByIdThenDenyAccess() {
    }

    @Test
    void givenUserExistsAndUserAdminWhenCallGetCustomerByIdThenReturnCustomerDetails() {
    }


    @Test
    void givenAdminUserWhenCallCreateCustomerThenReturnNewCustomer() {
        var newCustomer = CustomerModel.builder()
                .userName("Floris")
                .firstName("Floris")
                .lastName("Kraak")
                .email("randakar@gmail.com")
                .build();
        HttpEntity<CustomerModel> entity = new HttpEntity<>(newCustomer, httpHeaders);
        TestRestTemplate template = restTemplateForRole(ADMIN);
        ResponseEntity<CustomerViewModel> result = template.postForEntity("/customer", entity, CustomerViewModel.class);
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getStatusCode().is2xxSuccessful());

        var expectedResultBody = CustomerViewModel.builder()
                .id(0L)
                .userName(newCustomer.getUserName())
                .email(newCustomer.getEmail())
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .build();

        CustomerViewModel resultBody = result.getBody();
        assertNotNull(resultBody);
        assertEquals(expectedResultBody, resultBody);
    }


    public enum UserRole {
        NONE,
        USER,
        ADMIN
    }

    private final static Map<UserRole, Pair<String, String>> roleToUserCredsMap = Map.of(
            ADMIN, Pair.of("AlexanderKremer", "remerKrednaxelA"),
            USER, Pair.of("Ramyaa", "aaymaR"),
            NONE, Pair.of("FooUser", "")
    );

    private TestRestTemplate restTemplateForRole(UserRole role) {
        Pair<String, String> creds = ofNullable(roleToUserCredsMap.get(role)).orElseThrow(AssertionError::new);
        String userName = creds.getLeft();
        String password = creds.getRight();
        return testRestTemplate
                .withBasicAuth(userName, password);
    }


}
