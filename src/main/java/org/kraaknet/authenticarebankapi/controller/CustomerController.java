package org.kraaknet.authenticarebankapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.authenticarebankapi.controller.api.CustomerApi;
import org.kraaknet.authenticarebankapi.controller.model.CustomerDto;
import org.kraaknet.authenticarebankapi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;


    @Override
    public ResponseEntity<CustomerDto> getCurrentCustomer() {
        // Todo - get that id from the authentication context
        log.info("getCurrentCustomer()");
        return ResponseEntity.of(customerService.findCustomerById(1));
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(Long id) {
        log.info("getCustomerById({})", id);
        return ResponseEntity.of(customerService.findCustomerById(id));
    }


}
