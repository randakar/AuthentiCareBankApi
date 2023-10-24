package org.kraaknet.authenticarebankapi.service;

import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;

public interface TransactionAuthorizationService {

    default boolean authorizeTransaction(AccountEntity account, TransactionEntity transactionEntity) {

        // Implementation should check the authorization related fields in the model.
        // Also, probably convert these database models back into something else before passing it on.
        //
        // Mind - In real implementations the authorsation signature is way more involved,
        // probably involving a series of redirects between different systems and will probably get passed in with headers.
        // We don't do that here, obviously. Toy project, and getting to this point was hard enough in the time given.
        return true;
    }


}
