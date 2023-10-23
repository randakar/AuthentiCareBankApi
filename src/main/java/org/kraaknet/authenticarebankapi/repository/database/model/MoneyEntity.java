package org.kraaknet.authenticarebankapi.repository.database.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;

@Embeddable
@NoArgsConstructor(force = true)
@Getter
@Setter
public class MoneyEntity {

    @Basic(optional = false)
    @NonNull
    private String currency;

    @Basic(optional = false)
    @NonNull
    private Long amountInCents;

}
