package fr.ing.secu.leakybank.application.pages.login.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import fr.ing.secu.leakybank.application.pages.login.LoginForm;
import fr.ing.secu.leakybank.application.pages.login.UserDTO;
import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    
    User toDomain(LoginForm loginForm);
    UserDTO toDTO(Customer customer);
}