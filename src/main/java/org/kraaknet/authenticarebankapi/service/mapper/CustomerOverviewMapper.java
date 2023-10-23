package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.CustomerOverviewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AccountMapper.class, CardMapper.class}
)
public interface CustomerOverviewMapper {

    CustomerOverviewModel toOverviewModel(CustomerEntity customerEntity);
}
