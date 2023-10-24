package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.TransactionViewModel;
import org.kraaknet.authenticarebankapi.controller.model.TransferModel;
import org.kraaknet.authenticarebankapi.controller.model.WithDrawModel;
import org.kraaknet.authenticarebankapi.repository.database.model.AccountEntity;
import org.kraaknet.authenticarebankapi.repository.database.model.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {MoneyMapper.class}
)
public interface TransactionMapper {
    List<TransactionViewModel> toTransactionViewModels(List<TransactionEntity> transactionEntities);

    @Mapping(source = "fromIban", target = "from")
    @Mapping(source = "toIban", target = "to")
    TransactionViewModel toTransactionViewModel(TransactionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(source = "from", target = "fromIban")
    @Mapping(target = "toIban", ignore = true)
    @Mapping(target = "affectedAccounts", ignore = true)
    TransactionEntity fromWithdrawal(WithDrawModel withDrawModel);
    default TransactionEntity fromWithdrawal(WithDrawModel withDrawModel, AccountEntity fromAccount) {
        var result = fromWithdrawal(withDrawModel);
        result.setAffectedAccounts(List.of(fromAccount));
        return  result;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(source = "from", target = "fromIban")
    @Mapping(source = "to", target = "toIban")
    @Mapping(target = "affectedAccounts", ignore = true)
    TransactionEntity fromTransfer(TransferModel transferModel);

    default TransactionEntity fromTransfer(TransferModel transferModel, AccountEntity fromAccount, AccountEntity toAccount) {
        var result = fromTransfer(transferModel);
        result.setAffectedAccounts(List.of(fromAccount, toAccount));
        return  result;
    }
}
