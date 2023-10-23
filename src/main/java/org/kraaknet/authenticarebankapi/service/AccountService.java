package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.controller.exceptions.CreationFailedException;
import org.kraaknet.authenticarebankapi.controller.model.AccountModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountOverviewModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.repository.database.AccountRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.service.mapper.AccountMapper;
import org.kraaknet.authenticarebankapi.service.mapper.AccountOverviewMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final AccountMapper accountMapper;
    private final AccountOverviewMapper accountOverviewMapper;

    public AccountViewModel createAccount(AccountModel accountModel) {
        repository.findByIban(accountModel.getIban())
                .ifPresent(existingAccount -> {
                    throw new CreationFailedException("Account already exists."); });

        AccountEntity entity = accountMapper.toAccountEntity(accountModel);
        AccountEntity result = repository.save(entity);
        return accountMapper.toAccountViewModel(result);
    }

    public Optional<AccountViewModel> findAccountById(Long id) {
        return repository.findById(id)
                .map(accountMapper::toAccountViewModel);
    }

    public Optional<AccountOverviewModel> findAccountOverviewById(Long id) {
        return repository.findById(id)
                .map(accountOverviewMapper::toAccountOverviewModel);
    }
}
