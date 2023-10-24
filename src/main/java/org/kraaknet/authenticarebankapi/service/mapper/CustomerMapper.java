package org.kraaknet.authenticarebankapi.service.mapper;

import org.kraaknet.authenticarebankapi.controller.model.CustomerModel;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CustomerMapper  {

    CustomerViewModel toViewModel(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "cards", ignore = true)
    CustomerEntity toEntity(CustomerModel model);





}
