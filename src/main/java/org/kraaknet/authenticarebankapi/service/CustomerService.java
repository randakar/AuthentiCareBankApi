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
import org.kraaknet.authenticarebankapi.service.mapper.CustomerMapper;
import org.kraaknet.authenticarebankapi.service.mapper.CustomerOverviewMapper;
import org.kraaknet.authenticarebankapi.service.security.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final UserService userService;
    private final CustomerRepository repository;

    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;
    private final CustomerOverviewMapper customerOverviewMapper;


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
                .map(customerOverviewMapper::toOverviewModel);
    }

    public List<AccountViewModel> findAccountsForCustomerId(Long id) {
        return repository.findById(id)
                .map(CustomerEntity::getAccounts)
                .map(accountMapper::toAccountViewModels)
                .orElseThrow(NotFoundException::new);
    }

    public Optional<CustomerViewModel> findCurrentCustomer() {
        String userName = userService.getCurrentUser().getUsername();
        return findCustomerByUserName(userName);
    }

    public List<AccountViewModel> findMyAccounts() {
        String userName = userService.getCurrentUser().getUsername();
        return repository.findByUserName(userName)
                .map(CustomerEntity::getAccounts)
                .map(accountMapper::toAccountViewModels)
                .orElseThrow(NotFoundException::new);
    }
}
