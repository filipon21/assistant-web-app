package edu.ib.webapp.user.mapper;

import edu.ib.webapp.user.entity.Refferal;
import edu.ib.webapp.user.model.response.RefferalResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RefferalMapper {

    RefferalResponse refferalToRefferalResponse(Refferal refferal);
}
