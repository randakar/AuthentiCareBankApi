package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
@NoArgsConstructor(force = true)
@Getter
@Setter
public class AccountEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NaturalId
    @Column(nullable = false)
    private String iban;

    @NonNull
    @ManyToOne
    private CustomerEntity owner;

    @NonNull
    @Column(name = "balance", nullable = false)
    private Long balance;

    @NonNull
    @Column(name = "currency", nullable = false)
    private String currency;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @ManyToMany
    @OrderBy("timestamp ASC")
    private List<TransactionEntity> transactionHistory = new ArrayList<>();

}
