package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.exceptions.CreationFailedException;
import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.repository.database.CustomerRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.kraaknet.authenticarebankapi.service.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper customerMapper;


    public Optional<CustomerViewModel> findCustomerById(long id) {
        return repository.findById(id)
                .map(customerMapper::toViewModel);
    }

    public Optional<CustomerViewModel> findCustomerByUserName(@NonNull String userName) {
        return repository.findByUserName(userName);
    }

    public CustomerViewModel createCustomer(@NonNull CustomerModel customerModel) {
        findCustomerByUserName(customerModel.getUserName())
                .ifPresent(existingCustomer -> {
                    throw new CreationFailedException("Customer already exists."); });

        CustomerEntity result = repository.save(customerMapper.toEntity(customerModel));
        return customerMapper.toViewModel(result);

    }
}
