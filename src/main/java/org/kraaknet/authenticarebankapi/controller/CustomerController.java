package org.kraaknet.authenticarebankapi.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.authenticarebankapi.controller.api.CustomerApi;
import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.service.CustomerService;
import org.kraaknet.authenticarebankapi.service.security.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final UserService userService;
    private final CustomerService customerService;


    @Override
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<CustomerViewModel> getCurrentCustomer() {
        log.info("getCurrentCustomer()");
        String userName = userService.getCurrentUser().getUsername();
        return customerService.findCustomerByUserName(userName)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<CustomerViewModel> getCustomerById(Long id) {
        log.info("getCustomerById({})", id);
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<CustomerViewModel> createCustomer(CustomerModel customerModel) {
        return ResponseEntity.internalServerError().build();
    }
}
