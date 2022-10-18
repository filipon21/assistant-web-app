package edu.ib.webapp.user.service;

import edu.ib.webapp.user.model.dto.VisitPaginationDto;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitListResponse;
import edu.ib.webapp.user.model.response.VisitResponse;

public interface VisitService {

    VisitResponse createVisit(VisitRequest visitRequest, Long userId, Long assistantId, String username);

    VisitResponse updateVisit(Long id, VisitRequest visitRequest);

    VisitResponse getUserTeleVisit(Long id);

    VisitResponse getVisit(Long id);

    VisitListResponse getAllTelevisitsPaginated(VisitPaginationDto visitPaginationDto);
}
