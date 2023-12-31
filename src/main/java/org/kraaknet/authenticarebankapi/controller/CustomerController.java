package org.kraaknet.authenticarebankapi.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.authenticarebankapi.controller.api.CustomerApi;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerOverviewModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.service.CustomerService;
import org.kraaknet.authenticarebankapi.service.security.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;


    @Override
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<CustomerViewModel> getCurrentCustomer() {
        log.debug("getCurrentCustomer()");
        return customerService
                .findCurrentCustomer()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<CustomerViewModel> getCustomerById(Long id) {
        log.debug("getCustomerById({})", id);
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<CustomerViewModel> createCustomer(CustomerModel customerModel) {
        log.debug("createCustomer({})", customerModel);
        return ResponseEntity.ok(customerService.createCustomer(customerModel));
    }


    @Override
    public ResponseEntity<CustomerOverviewModel> getCustomerOverview(Long id) {
        return customerService.findCustomerOverview(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<AccountViewModel>> getCustomerAccounts(Long id) {
        List<AccountViewModel> result = customerService.findAccountsForCustomerId(id);
        return ResponseEntity.ok(result);
    }
}
