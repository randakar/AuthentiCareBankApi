package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TransactionEntity.TABLE_NAME)
@NoArgsConstructor(force = true)
@Getter
@Setter
public class TransactionEntity {

    public static final String ENTITY_NAME = "";
    public static final String TABLE_NAME = "transaction";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition= "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private LocalDateTime timestamp = LocalDateTime.now();

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
}
