package com.company.MiniBankWebAppByUsingRest.mapper;

import com.company.MiniBankByUsingSpring.entity.Transaction;
import com.company.MiniBankWebAppByUsingRest.dto.TransactionDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "fromAccount.id", target = "fromAccountId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "fromAccountId", target = "fromAccount.id")
    Transaction toEntity(TransactionDTO transactionDto);

    List<TransactionDTO> toDtoList(List<Transaction> transaction);

}
