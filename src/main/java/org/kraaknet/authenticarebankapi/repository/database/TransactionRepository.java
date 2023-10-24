package org.kraaknet.authenticarebankapi.repository.database;

import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

}
