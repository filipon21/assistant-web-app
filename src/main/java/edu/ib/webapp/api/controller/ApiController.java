package edu.ib.webapp.api.controller;

import edu.ib.webapp.api.model.dto.AssistantPaginationDto;
import edu.ib.webapp.api.model.dto.AssistantSearchingParams;
import edu.ib.webapp.api.model.response.AssistantListResponse;
import edu.ib.webapp.api.pagination.PaginationSupport;
import edu.ib.webapp.api.service.ApiService;
import edu.ib.webapp.common.pagination.SortingParamsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/user")
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/assistant")
    public AssistantListResponse getAllTemplates(@RequestParam Integer pageNumber,
                                                 @RequestParam Integer pageSize,
                                                 @RequestParam(required = false) String sortParameter,
                                                 @RequestParam(required = false) String sortDirection,
                                                 @RequestParam(required = false) String userFirstName,
                                                 @RequestParam(required = false) String userLastName,
                                                 @RequestParam(required = false) String phoneNumber,
                                                 @RequestParam(required = false) Boolean isOnline)
                                                 {

        SortingParamsDto sortingParams = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        AssistantSearchingParams searchingParams = PaginationSupport.getAssistantSearchingParams(userFirstName, userLastName,
                phoneNumber, isOnline);
        AssistantPaginationDto paginationDto = PaginationSupport.getAssistantPaginationDto(pageNumber, pageSize, sortingParams,
                searchingParams);

        return apiService.getAllPaginated(paginationDto);
    }
}
