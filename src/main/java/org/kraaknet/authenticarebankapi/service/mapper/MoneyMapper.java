package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.MoneyModel;
import org.kraaknet.authenticarebankapi.repository.database.model.MoneyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MoneyMapper {

    default MoneyModel toMoneyModel(MoneyEntity moneyEntity) {
        var result = new MoneyModel();
        result.amount(BigDecimal.valueOf(moneyEntity.getAmountInCents() / 100));
        result.currency(moneyEntity.getCurrency());
        return result;
    }

    default MoneyEntity toMoneyEntity(MoneyModel moneyModel) {
        var result = new MoneyEntity();
        result.setCurrency(moneyModel.getCurrency());
        result.setAmountInCents(moneyModel.getAmount().longValue() * 100);
        return result;
    }

}
