package org.kraaknet.authenticarebankapi.repository.database;

import org.kraaknet.authenticarebankapi.repository.database.model.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
