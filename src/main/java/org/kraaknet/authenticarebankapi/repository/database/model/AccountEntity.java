package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.*;
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

    @NaturalId
    @Basic(optional = false)
    @NonNull
    private String iban;

    @Basic(optional = false)
    @NonNull
    private String name;

    @Embedded
    @NonNull
    private MoneyEntity balance;

    @Basic(optional = false)
    @NonNull
    private String description;


    @ManyToMany
    @JoinTable
    @NonNull
    private List<CustomerEntity> owners;

    @OneToOne
    @NonNull
    private CardEntity card;


    @ManyToMany
    @JoinTable
    @OrderBy("timestamp ASC")
    @NonNull
    private List<TransactionEntity> transactionHistory = new ArrayList<>();

}
