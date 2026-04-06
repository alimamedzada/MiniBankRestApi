package com.company.MiniBankWebAppByUsingRest.mapper;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankWebAppByUsingRest.dto.AccountsDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface AccountsMapper {

    @Mapping(source = "customer.username", target = "customerUsername")
    @Mapping(target = "accountType", expression = "java(account.getClass().getSimpleName().replace(\"Account\", \"\"))")
    @Mapping(source = "customer.customerId", target = "customerId")
    AccountsDTO toDto(Accounts account);

    @Mapping(source = "customerId", target = "customer.customerId")
    Accounts toEntity(AccountsDTO accountDto);

    List<AccountsDTO> toDtoList(List<Accounts> accounts);
}
