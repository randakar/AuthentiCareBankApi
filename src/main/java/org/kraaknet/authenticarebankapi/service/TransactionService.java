package org.kraaknet.authenticarebankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kraaknet.authenticarebankapi.repository.database.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;

}
