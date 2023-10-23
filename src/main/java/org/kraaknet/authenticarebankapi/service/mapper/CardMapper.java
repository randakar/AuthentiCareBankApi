package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.CardModel;
import org.kraaknet.authenticarebankapi.repository.database.model.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CardMapper {
    List<CardModel> toCardModels(List<CardEntity> cards);

    @Mapping(source = "owner.id", target = "customerId")
    @Mapping(source = "account.id", target = "accountId")
    CardModel toCardModel(CardEntity cardEntity);
}
