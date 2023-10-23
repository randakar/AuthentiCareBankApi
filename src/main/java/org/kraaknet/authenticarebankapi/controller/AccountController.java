package org.kraaknet.authenticarebankapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.authenticarebankapi.controller.api.AccountApi;
import org.kraaknet.authenticarebankapi.controller.model.*;
import org.kraaknet.authenticarebankapi.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountViewModel> createAccount(AccountModel accountModel) {
       return ResponseEntity.ok(accountService.createAccount(accountModel));
    }

    @Override
    public ResponseEntity<AccountViewModel> getAccountById(Long id) {
        return AccountApi.super.getAccountById(id);
    }

    @Override
    public ResponseEntity<AccountOverviewModel> getAccountOvervieuwById(Long id) {
        return AccountApi.super.getAccountOvervieuwById(id);
    }

    @Override
    public ResponseEntity<List<AccountViewModel>> getMyAccounts() {
        return AccountApi.super.getMyAccounts();
    }

    @Override
    public ResponseEntity<List<TransactionViewModel>> getTransactions(Long id) {
        return AccountApi.super.getTransactions(id);
    }

    @Override
    public ResponseEntity<Void> transferAmount(Long id, TransferModel transferModel) {
        return AccountApi.super.transferAmount(id, transferModel);
    }

    @Override
    public ResponseEntity<Void> withdrawAmount(Long id, WithDrawModel withDrawModel) {
        return AccountApi.super.withdrawAmount(id, withDrawModel);
    }
}
