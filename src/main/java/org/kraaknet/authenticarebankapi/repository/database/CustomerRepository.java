package org.kraaknet.authenticarebankapi.repository.database;

import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerViewModel> findByUserName(String userName);

    CustomerEntity create(CustomerEntity entity);
}
