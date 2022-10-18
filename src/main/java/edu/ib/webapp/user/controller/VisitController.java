package edu.ib.webapp.user.controller;

import edu.ib.webapp.common.pagination.SortingParamsDto;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.model.dto.VisitPaginationDto;
import edu.ib.webapp.user.model.dto.VisitSearchingParamsDto;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitListResponse;
import edu.ib.webapp.user.model.response.VisitResponse;
import edu.ib.webapp.user.pagination.PaginationSupport;
import edu.ib.webapp.user.pagination.VisitPaginationSupport;
import edu.ib.webapp.user.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('USER', 'ASSISTANT')")
@RequestMapping("/api/visit")
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/{userId}/{hostId}")
    public VisitResponse createVisit(@RequestBody VisitRequest visitRequest, @PathVariable Long userId,
                                     @PathVariable Long hostId, Authentication authentication) {
        return visitService.createVisit(visitRequest, userId, hostId, authentication.getName());
    }

    @PatchMapping("/{id}")
    public VisitResponse updateVisit(@PathVariable Long id, @RequestBody VisitRequest visitRequest) {
        return visitService.updateVisit(id, visitRequest);
    }

    @GetMapping("/online/{userId}")
    public VisitResponse getUserVisits(@PathVariable Long userId){
        return visitService.getUserTeleVisit(userId);
    }

    @GetMapping("/{id}")
    public VisitResponse getVisit(@PathVariable Long id){
        return visitService.getVisit(id);
    }

    @GetMapping
    public VisitListResponse getAllUserTelevisits(@RequestParam Integer pageNumber,
                                                  @RequestParam Integer pageSize,
                                                  @RequestParam(required = false) String sortParameter,
                                                  @RequestParam(required = false) String sortDirection,
                                                  @RequestParam Long userId,
                                                  @RequestParam(required = false) VisitStatusEnum visitStatusEnum,
                                                  @RequestParam(required = false) VisitTypeEnum visitTypeEnum,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime startTime,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime endTime
    ){
        SortingParamsDto sortingParamsDto = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        VisitSearchingParamsDto searchingParamsDto = VisitPaginationSupport.getTelevisitSearchingParams(userId,
                visitStatusEnum, visitTypeEnum, startTime, endTime);
        VisitPaginationDto paginationDto = VisitPaginationSupport.getTelevisitPaginationDto(pageNumber, pageSize,
                sortingParamsDto, searchingParamsDto);
        return visitService.getAllTelevisitsPaginated(paginationDto);
    }
}
