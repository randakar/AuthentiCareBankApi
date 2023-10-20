package org.kraaknet.authenticarebankapi.controller;

import jakarta.validation.constraints.NotNull;
import org.kraaknet.authenticarebankapi.controller.api.CustomerApi;
import org.kraaknet.authenticarebankapi.controller.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${openapi.api.base-path:}")
public class CustomerController implements CustomerApi {

    @Override
    public ResponseEntity<Customer> getCurrentCustomer() {
        return ResponseEntity.ofNullable(Customer.builder()
                .id(1L)
                .email("randakar@gmail.com")
                .firstname("Foo")
                .lastname("Bar")
                .build());
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(@NotNull Integer id) {
        return getCurrentCustomer();
    }


}
