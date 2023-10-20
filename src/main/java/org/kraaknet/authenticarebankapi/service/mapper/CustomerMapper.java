package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.CustomerDto;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerMapper implements Function<CustomerEntity, CustomerDto> {

    @Override
    public CustomerDto apply(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .firstname(customerEntity.getFirstName())
                .lastname(customerEntity.getLastName())
                .build();
    }
}
