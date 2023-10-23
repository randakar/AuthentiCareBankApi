package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.repository.database.TransactionRepository;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;

    public List<TransactionEntity> findTransactionsByAffectedAccountId(AccountEntity account) {
        return transactionRepository.findAllByAffectedAccountsOrderByTimestamp(List.of(account));
    }
}
