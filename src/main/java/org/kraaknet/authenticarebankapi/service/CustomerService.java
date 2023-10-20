package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.controller.model.CustomerDto;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerRepository;
import org.kraaknet.authenticarebankapi.service.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    public Optional<CustomerDto> findCustomerById(long id) {
        return customerRepository.findById(id)
                .map(customerMapper);
    }
}
