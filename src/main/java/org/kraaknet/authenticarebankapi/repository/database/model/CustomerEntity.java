package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@Getter
@Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    @Nullable
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Nullable
    private String lastName;

    @Column(name = "email")
    @Nullable
    private String email;

    @OneToMany(mappedBy="owner")
    @OrderBy("name ASC")
    @NonNull
    private List<AccountEntity> accounts = new ArrayList<>();
}