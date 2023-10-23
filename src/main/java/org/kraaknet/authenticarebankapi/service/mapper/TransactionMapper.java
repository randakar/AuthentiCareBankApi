package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.TransactionViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;
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
public interface TransactionMapper {
    List<TransactionViewModel> toTransactionViewModels(List<TransactionEntity> transactionEntities);

    @Mapping(source = "fromIban", target = "from")
    @Mapping(source = "toIban", target = "to")
    TransactionViewModel toTransactionViewModel(TransactionEntity entity);
}
