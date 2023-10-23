package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.AccountModel;
import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {MoneyMapper.class}
)
public interface AccountMapper {

    MoneyMapper moneyMapper = Mappers.getMapper(MoneyMapper.class);

    List<AccountViewModel> toAccountViewModels(List<AccountEntity> accounts);


    default AccountViewModel toAccountViewModel(AccountEntity accountEntity) {
        var result = new AccountViewModel();
        result.id(accountEntity.getId());
        result.name(accountEntity.getName());
        result.description(accountEntity.getDescription());
        result.balance(moneyMapper.toMoneyModel(accountEntity.getBalance()));
        return result;
    }

    // Interesting how this list of fields to ignore corresponds pretty exactly to
    // what you do not expect brand-new accounts to have.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owners", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "transactionHistory", ignore = true)
    AccountEntity toAccountEntity(AccountModel accountModel);
}
