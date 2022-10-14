package fr.ing.secu.leakybank.infrastructure.transaction.mapper;

import fr.ing.secu.leakybank.domain.transaction.Transaction;
import fr.ing.secu.leakybank.infrastructure.transaction.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    Transaction toDomain(TransactionEntity transactionEntity);
}
