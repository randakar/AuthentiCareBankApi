package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.exceptions.CreationFailedException;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotFoundException;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerOverviewModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.repository.database.CustomerRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.kraaknet.authenticarebankapi.service.mapper.AccountMapper;
import org.kraaknet.authenticarebankapi.service.mapper.CardMapper;
import org.kraaknet.authenticarebankapi.service.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;
    private final CardMapper cardMapper;


    public Optional<CustomerViewModel> findCustomerById(long id) {
        return repository.findById(id)
                .map(customerMapper::toViewModel);
    }

    public Optional<CustomerViewModel> findCustomerByUserName(@NonNull String userName) {
        return repository.findByUserName(userName)
                .map(customerMapper::toViewModel);
    }

    public CustomerViewModel createCustomer(@NonNull CustomerModel customerModel) {
        findCustomerByUserName(customerModel.getUserName())
                .ifPresent(existingCustomer -> {
                    throw new CreationFailedException("Customer already exists."); });

        CustomerEntity result = repository.save(customerMapper.toEntity(customerModel));
        return customerMapper.toViewModel(result);

    }

    public Optional<CustomerOverviewModel> findCustomerOverview(long id) {
        return repository.findById(id)
                .map(customerEntity -> customerMapper.toOverviewModel(customerEntity,
                        customerEntity.getAccounts(),
                        customerEntity.getCards()));
    }

    public List<AccountViewModel> findAccountsForCustomerId(Long id) {
        return repository.findById(id)
                .map(CustomerEntity::getAccounts)
                .map(accountMapper::toAccountViewModels)
                .orElseThrow(NotFoundException::new);
    }
}
