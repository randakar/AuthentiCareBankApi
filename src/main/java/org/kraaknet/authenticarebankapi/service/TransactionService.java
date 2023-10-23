package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kraaknet.authenticarebankapi.controller.exceptions.NotAuthorizedException;
import org.kraaknet.authenticarebankapi.controller.model.TransferModel;
import org.kraaknet.authenticarebankapi.controller.model.WithDrawModel;
import org.kraaknet.authenticarebankapi.repository.database.TransactionRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.MoneyEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;
import org.kraaknet.authenticarebankapi.service.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionAuthorizationService transactionAuthorizationService;
    private final TransactionMapper transactionMapper;


    public TransactionEntity transferAmountFromAccount(AccountEntity fromAccount, AccountEntity toAccount, TransferModel transferModel) {
        TransactionEntity newTransaction = transactionMapper.fromTransfer(transferModel, fromAccount, toAccount);
        TransactionEntity verifiedTransaction = verifyTransaction(fromAccount, toAccount, newTransaction);
        return repository.save(verifiedTransaction);
    }

    public TransactionEntity withdrawAmountFromAccount(AccountEntity fromAccount, WithDrawModel withDrawModel) {
        TransactionEntity newTransaction = transactionMapper.fromWithdrawal(withDrawModel, fromAccount);
        TransactionEntity verifiedTransaction = verifyTransaction(fromAccount, newTransaction);
        return repository.save(verifiedTransaction);
    }

    // Todo: Turn this into a JSR-303 Bean validation annotation
    private TransactionEntity verifyTransaction(AccountEntity fromAccount, TransactionEntity newTransaction) {
        return verifyTransaction(fromAccount, null, newTransaction);
    }

    private TransactionEntity verifyTransaction(AccountEntity fromAccount, @Nullable AccountEntity toAccount, TransactionEntity newTransaction) {
        MoneyEntity balance = fromAccount.getBalance();
        MoneyEntity amount = newTransaction.getAmount();
        String currency = amount.getCurrency();
        if (!StringUtils.equalsAnyIgnoreCase(balance.getCurrency(), currency)) {
            throw new UnsupportedOperationException(); // Todo: Handle this in the ControllerAdvice class
        }

        // Neither of these should be reachable.
        if(!StringUtils.equalsAnyIgnoreCase(fromAccount.getIban(), newTransaction.getFromIban())) {
            throw new ValidationException("From account does not match Iban"); // Or perhaps a custom InvalidTransactionException?
        }

        Optional.ofNullable(toAccount.getIban()).ifPresent(toIban -> {
            if (!StringUtils.equalsAnyIgnoreCase(toIban, newTransaction.getToIban())) {
                throw new ValidationException("To account does not match Iban"); // Or perhaps a custom InvalidTransactionException?
            }
        });



        long newAmountInCents = calculateCharges(newTransaction, amount);
        MoneyEntity newAmount = amountOf(currency, newAmountInCents);
        TransactionEntity verifiedTransaction = transactionWithNewAmount(newTransaction, newAmount);

        if (!transactionAuthorizationService.authorizeTransaction(fromAccount, verifiedTransaction)) {
            throw new NotAuthorizedException();
        }
        return verifiedTransaction;
    }

    private TransactionEntity transactionWithNewAmount(TransactionEntity oldTransaction, MoneyEntity newAmount) {
        // This would be a lot less code if I could put a Builder on JPA entities..
        var result = new TransactionEntity();
        result.setAmount(newAmount);
        result.setTransactionType(oldTransaction.getTransactionType());
        result.setTimestamp(Instant.now());
        result.setFromIban(oldTransaction.getFromIban());
        result.setToIban(oldTransaction.getToIban());
        result.setDescription(oldTransaction.getDescription());
        result.setAuthorizationMethod(result.getAuthorizationMethod());
        result.setTransferType(oldTransaction.getTransferType());
        result.setAffectedAccounts(oldTransaction.getAffectedAccounts());
        result.setAuthorizationSignature(oldTransaction.getAuthorizationSignature());
        return result;
    }

    private static long calculateCharges(TransactionEntity newTransaction, MoneyEntity amount) {
        boolean isCreditcard = StringUtils.equalsAnyIgnoreCase("creditcard", newTransaction.getAuthorizationMethod());
        long amountInCents = amount.getAmountInCents();
        double charges = isCreditcard ? amountInCents * 0.01 : 0.0;
        return amountInCents + ((Double) charges).longValue();
    }

    private static MoneyEntity amountOf(String currency, long newAmountInCents) {
        var result = new MoneyEntity();
        result.setCurrency(currency);
        result.setAmountInCents(newAmountInCents);
        return result;
    }


}
