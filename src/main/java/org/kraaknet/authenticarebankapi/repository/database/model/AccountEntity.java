package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private String accountIBan;

    @NonNull
    @ManyToOne
    private UserEntity owner;

    @NonNull
    @Column(name = "balance", nullable = false)
    private Long balance;

    @NonNull
    @Column(name = "currency")
    private String currency;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @OneToMany(mappedBy="account")
    @OrderBy("date ASC")
    private List<TransactionEntity> transactionHistory = new ArrayList<>();

}
