package org.kraaknet.authenticarebankapi.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.controller.api.CustomerApi;
import org.kraaknet.authenticarebankapi.controller.model.CustomerDto;
import org.kraaknet.authenticarebankapi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${openapi.api.base-path:}")
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;


    @Override
    public ResponseEntity<CustomerDto> getCurrentCustomer() {
        // Todo - get that id from the authentication context
        return ResponseEntity.of(customerService.findCustomerById(1));
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerById(@NotNull Long id) {
        return ResponseEntity.of(customerService.findCustomerById(id));
    }


}
