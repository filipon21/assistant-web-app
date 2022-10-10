package edu.ib.webapp.user.service;

import edu.ib.webapp.user.model.request.TelevisitRequest;
import edu.ib.webapp.user.model.response.TelevisitResponse;

import java.util.List;

public interface TelevisitService {

    TelevisitResponse createVisit(TelevisitRequest televisitRequest, Long userId, Long assistantId, String username);

    TelevisitResponse updateVisit(Long id, TelevisitRequest televisitRequest);

    List<TelevisitResponse> getUserVisits(Long id);

    TelevisitResponse getVisit(Long id);
}
