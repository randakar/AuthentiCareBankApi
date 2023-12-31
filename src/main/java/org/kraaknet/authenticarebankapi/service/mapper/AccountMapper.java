package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.AccountModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {MoneyMapper.class}
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
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "transactionHistory", ignore = true)
    AccountEntity toAccountEntity(AccountModel accountModel);

}
