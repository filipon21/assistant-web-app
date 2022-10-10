package edu.ib.webapp.user.mapper;

import edu.ib.webapp.user.entity.Televisit;
import edu.ib.webapp.user.model.request.TelevisitRequest;
import edu.ib.webapp.user.model.response.TelevisitResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TelevisitMapper {

    TelevisitResponse visitToVisitResponse(Televisit televisit);

    Televisit visitRequestToVisit(TelevisitRequest televisitRequest);
}
