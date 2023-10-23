package org.kraaknet.authenticarebankapi.service;

import org.kraaknet.authenticarebankapi.controller.model.TransferModel;
import org.kraaknet.authenticarebankapi.controller.model.WithDrawModel;

public interface TransactionAuthorizationService {

    default boolean authorizeWithDrawal(WithDrawModel withdrawal) {

        // Implementation should check the authorization related fields in the model.
        // Mind - In real implementations the authorzation signature is way more involved,
        // probably involving a series of redirects between different systems and will probably get passed in with headers.
        // We don't do that here, obviously. Toy project, and getting to this point was hard enough in the time given.
        return true;
    }

    default boolean authorizeTransfer(TransferModel transferModel)  {
        return true;
    }

}
