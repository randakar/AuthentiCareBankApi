package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.AccountOverviewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {MoneyMapper.class, CustomerMapper.class, CardMapper.class}
)
public interface AccountOverviewMapper {

    AccountOverviewModel toAccountOverviewModel(AccountEntity account);


}
