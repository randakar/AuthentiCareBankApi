package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.AccountViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.mapstruct.Mapper;
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


}
