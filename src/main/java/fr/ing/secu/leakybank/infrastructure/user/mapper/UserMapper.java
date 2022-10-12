package fr.ing.secu.leakybank.infrastructure.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.infrastructure.user.entity.CustomerEntity;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    Customer toDomain(CustomerEntity customerEntity);
}
