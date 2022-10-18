package edu.ib.webapp.user.mapper;

import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.model.dto.VisitInfoDto;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VisitMapper {

    VisitResponse visitToVisitResponse(Visit visit);

    Visit visitRequestToVisit(VisitRequest visitRequest);

    VisitInfoDto televisitToTelevisitInfoDto(Visit visit);
}
