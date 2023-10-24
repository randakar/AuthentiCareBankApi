package org.kraaknet.authenticarebankapi.repository.database;

import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByIban(String iban);
}
