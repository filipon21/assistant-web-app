package edu.ib.webapp.user.mapper;

import edu.ib.webapp.user.entity.Exemption;
import edu.ib.webapp.user.model.response.ExemptionResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ExemptionMapper {

    ExemptionResponse exemptionToExemptionResponse(Exemption exemption);

}
