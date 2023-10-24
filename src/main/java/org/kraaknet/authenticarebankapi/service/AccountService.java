package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.kraaknet.authenticarebankapi.controller.exceptions.CreationFailedException;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotAuthorizedException;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotFoundException;
import org.kraaknet.authenticarebankapi.controller.model.*;
import org.kraaknet.authenticarebankapi.repository.database.AccountRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.MoneyEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;
import org.kraaknet.authenticarebankapi.service.mapper.AccountMapper;
import org.kraaknet.authenticarebankapi.service.mapper.AccountOverviewMapper;
import org.kraaknet.authenticarebankapi.service.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final AccountMapper accountMapper;
    private final AccountOverviewMapper accountOverviewMapper;
    private final TransactionMapper transactionMapper;

    private final TransactionService transactionService;

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

    public List<TransactionViewModel> getTransactionsByAccountId(Long id) {
        return repository.findById(id)
                .map(AccountEntity::getTransactionHistory)
                .map(transactionMapper::toTransactionViewModels)
                .orElseThrow(NotFoundException::new);
    }

    public TransactionEntity transferAmountFromAccount(Long id, TransferModel transferModel) {
        var fromAccount = repository.findById(id).orElseThrow(NotFoundException::new);
        var toAccount = repository.findByIban(transferModel.getTo()).orElseThrow(NotFoundException::new);
        if(!StringUtils.equalsAnyIgnoreCase(transferModel.getFrom(), fromAccount.getIban())) {
            throw new ValidationException("Invalid from account in transfer"); // or perhaps a notfound exception as well, for security.
        }
        var result = transactionService.transferAmountFromAccount(fromAccount, toAccount, transferModel);
        updateBalance(fromAccount, result);
        return result;
    }

    public TransactionEntity withdrawAmountFromAccount(Long id, WithDrawModel withDrawModel) {
        var fromAccount = repository.findById(id).orElseThrow(NotFoundException::new);
        var result = transactionService.withdrawAmountFromAccount(fromAccount, withDrawModel);
        updateBalance(fromAccount, result);
        return result;
    }

    private static void updateBalance(AccountEntity fromAccount, TransactionEntity result) {
        MoneyEntity oldBalance = fromAccount.getBalance();
        Long oldBalanceInCents = oldBalance.getAmountInCents();
        long newBalanceInCents = oldBalanceInCents - result.getAmount().getAmountInCents();
        if(newBalanceInCents < 0) {
            throw new NotAuthorizedException(); // Should not be reachable, we verify this elsewhere as well.
        }
        MoneyEntity newBalance = new MoneyEntity();
        newBalance.setCurrency(oldBalance.getCurrency());
        newBalance.setAmountInCents(newBalanceInCents);
        fromAccount.setBalance(newBalance);
    }



}
