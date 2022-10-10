package edu.ib.webapp.api.service;

import edu.ib.webapp.api.model.dto.AssistantPaginationDto;
import edu.ib.webapp.api.model.response.AssistantListResponse;

public interface ApiService {
    AssistantListResponse getAllPaginated(AssistantPaginationDto templatePaginationDto);

}
