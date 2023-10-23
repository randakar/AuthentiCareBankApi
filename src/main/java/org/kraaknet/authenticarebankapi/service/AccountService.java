package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.controller.exceptions.CreationFailedException;
import org.kraaknet.authenticarebankapi.controller.model.AccountModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.repository.database.AccountRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    public AccountViewModel createAccount(AccountModel accountModel) {
        repository.findByIban(accountModel.getIban())
                .ifPresent(existingAccount -> {
                    throw new CreationFailedException("Account already exists."); });

        AccountEntity entity = mapper.toAccountEntity(accountModel);
        AccountEntity result = repository.save(entity);
        return mapper.toAccountViewModel(result);
    }
}
