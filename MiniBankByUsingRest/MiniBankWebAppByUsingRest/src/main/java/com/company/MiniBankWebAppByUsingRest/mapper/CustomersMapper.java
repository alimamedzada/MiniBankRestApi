package com.company.MiniBankWebAppByUsingRest.mapper;

import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountsMapper.class})
public interface CustomersMapper {

    CustomersDTO toDto(Customers customer);

    @Mapping(target = "accounts", ignore = true)
    Customers toEntity(CustomersDTO customerDto);
}
