package org.kraaknet.authenticarebankapi.service.mapper;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.kraaknet.authenticarebankapi.controller.model.CustomerViewModel;
import org.kraaknet.authenticarebankapi.repository.database.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.function.Function;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CustomerMapper extends Function<@NonNull CustomerEntity, CustomerViewModel> {

    @Override
    CustomerViewModel apply(CustomerEntity customerEntity);
}
