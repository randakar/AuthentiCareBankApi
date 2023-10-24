package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction")
@NoArgsConstructor(force = true)
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition= "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Instant timestamp = Instant.now();

    @Basic
    @Nullable
    private String fromIban;

    @Basic
    @Nullable
    private String toIban;

    @ManyToMany
    @JoinTable
    // May affect 1 or 2 accounts, since accounts can be at different banks.
    @NonNull
    private List<AccountEntity> affectedAccounts = new ArrayList<>();

    @Embedded
    private MoneyEntity amount;

    @Column
    @NonNull
    private String description;

    @Basic
    private String transactionType;

    @Basic
    private String transferType;

    @Basic
    private String authorizationMethod;

    @Basic
    private String authorizationSignature;

}
