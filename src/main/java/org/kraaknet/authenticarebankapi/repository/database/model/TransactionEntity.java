package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;

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

    @NonNull
    @Column(columnDefinition= "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp = LocalDateTime.now();

    @NonNull
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private AccountEntity fromAccount;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private AccountEntity toAccount;

    @ManyToMany
    private List<AccountEntity> affectedAccounts = new ArrayList<>();

    @NonNull
    @Column
    private Long amount;
}
