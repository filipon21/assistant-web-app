package edu.ib.webapp.user.service;

import edu.ib.webapp.user.model.dto.VisitPaginationDto;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitListResponse;
import edu.ib.webapp.user.model.response.VisitResponse;

public interface VisitService {

    VisitResponse createTeleVisit(VisitRequest visitRequest, Long userId, Long hostId, String username);

    VisitResponse createVisit(VisitRequest visitRequest, Long hostId);

    VisitResponse addUserToVisit(Long refferalId, Long userId, Long visitId);

    VisitResponse deleteUserFromVisit(Long refferalId, Long userId, Long visitId);

    VisitResponse updateVisit(Long id, VisitRequest visitRequest);

    VisitResponse getUserTeleVisit(Long id);

    VisitResponse getVisit(Long id);

    VisitListResponse getAllHistoryVisitsPaginated(VisitPaginationDto visitPaginationDto, String name);

    VisitListResponse getAllFreeVisitsPaginated(VisitPaginationDto visitPaginationDto);

    VisitListResponse getAllUpcomingVisitsPaginated(VisitPaginationDto paginationDto, String name);

    void sendSMSReminder();
}
