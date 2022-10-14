package fr.ing.secu.leakybank.application.pages.transfer.mapper;

import fr.ing.secu.leakybank.application.pages.transfer.TransferForm;
import fr.ing.secu.leakybank.domain.transaction.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransferMapper {
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);
    Transfer toDomain(TransferForm transferForm);

}
