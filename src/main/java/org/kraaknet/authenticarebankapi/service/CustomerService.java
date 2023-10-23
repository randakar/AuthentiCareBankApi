package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.exceptions.CreationFailedException;
import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerOverviewModel;
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
    private final CustomerMapper mapper;

    private final AccountService accountService;
    private final CardService cardService;


    public Optional<CustomerViewModel> findCustomerById(long id) {
        return repository.findById(id)
                .map(mapper::toViewModel);
    }

    public Optional<CustomerViewModel> findCustomerByUserName(@NonNull String userName) {
        return repository.findByUserName(userName)
                .map(mapper::toViewModel);
    }

    public CustomerViewModel createCustomer(@NonNull CustomerModel customerModel) {
        findCustomerByUserName(customerModel.getUserName())
                .ifPresent(existingCustomer -> {
                    throw new CreationFailedException("Customer already exists."); });

        CustomerEntity result = repository.save(mapper.toEntity(customerModel));
        return mapper.toViewModel(result);

    }

    public Optional<CustomerOverviewModel> findCustomerOverview(long id) {
        return repository.findById(id)
                .map(customerEntity -> mapper.toOverviewModel(customerEntity,
                        accountService.findAccountsByCustomer(customerEntity),
                        cardService.findCardsByOwner(customerEntity)));
    }
}
