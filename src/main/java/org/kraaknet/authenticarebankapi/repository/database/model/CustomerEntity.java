package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
@Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    @NaturalId
    private String userName;

    @Column(name = "first_name", nullable = false)
    @Nullable
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Nullable
    private String lastName;

    @Column(name = "email")
    @Nullable
    private String email;

    @ManyToMany
    @JoinTable
    @OrderBy("name ASC")
    @NonNull
    private List<AccountEntity> accounts = new ArrayList<>();

    @OneToMany
    @NonNull
    private List<CardEntity> cards;
}
