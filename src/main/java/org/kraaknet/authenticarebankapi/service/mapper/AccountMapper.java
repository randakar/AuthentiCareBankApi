package org.kraaknet.authenticarebankapi.service.mapper;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.model.AccountModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountOverviewModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.CardEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {MoneyMapper.class, CustomerMapper.class, CardMapper.class}
)
public interface AccountMapper {

    List<AccountViewModel> toAccountViewModels(List<AccountEntity> accounts);

    default AccountViewModel toAccountViewModel(AccountEntity accountEntity) {
        List<Long> owners = accountEntity.getOwners().stream().map(CustomerEntity::getId).toList();
        return toAccountViewModel(accountEntity, owners);
    }

    @Mapping(source = "owners", target = "owners")
    AccountViewModel toAccountViewModel(AccountEntity accountEntity, List<Long> owners);

    // Interesting how this list of fields to ignore corresponds pretty exactly to
    // what you do not expect brand-new accounts to have.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owners", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "transactionHistory", ignore = true)
    AccountEntity toAccountEntity(AccountModel accountModel);

    @Mapping(source = "owners", target="owners")
    @Mapping(source = "cards", target="cards")
    AccountOverviewModel toAccountOverviewModel(AccountEntity account, @NonNull List<CustomerEntity> owners, @NonNull List<CardEntity> cards);

}
