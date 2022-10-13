package edu.ib.webapp.user.controller;

import edu.ib.webapp.common.pagination.SortingParamsDto;
import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
import edu.ib.webapp.user.model.dto.TelevisitPaginationDto;
import edu.ib.webapp.user.model.dto.TelevisitSearchingParamsDto;
import edu.ib.webapp.user.model.request.TelevisitRequest;
import edu.ib.webapp.user.model.response.TelevisitListResponse;
import edu.ib.webapp.user.model.response.TelevisitResponse;
import edu.ib.webapp.user.pagination.PaginationSupport;
import edu.ib.webapp.user.pagination.TelevisitPaginationSupport;
import edu.ib.webapp.user.service.TelevisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('USER', 'ASSISTANT')")
@RequestMapping("/api/visit")
public class TelevisitController {

    private final TelevisitService televisitService;

    @PostMapping("/{userId}/{assistantId}")
    public TelevisitResponse createVisit(@RequestBody TelevisitRequest televisitRequest, @PathVariable Long userId,
                                         @PathVariable Long assistantId, Authentication authentication) {
        return televisitService.createVisit(televisitRequest, userId, assistantId, authentication.getName());
    }

    @PatchMapping("/{id}")
    public TelevisitResponse updateVisit(@PathVariable Long id, @RequestBody TelevisitRequest televisitRequest) {
        return televisitService.updateVisit(id, televisitRequest);
    }

    @GetMapping("/list/{userId}")
    public List<TelevisitResponse> getUserVisits(@PathVariable Long userId){
        return televisitService.getUserVisits(userId);
    }

    @GetMapping("/{id}")
    public TelevisitResponse getVisit(@PathVariable Long id){
        return televisitService.getVisit(id);
    }

    @GetMapping
    public TelevisitListResponse getAllUserTelevisits(@RequestParam Integer pageNumber,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam(required = false) String sortParameter,
                                                      @RequestParam(required = false) String sortDirection,
                                                      @RequestParam Long userId,
                                                      @RequestParam(required = false) TelevisitStatusEnum televisitStatusEnum,
                                                      @RequestParam(required = false) TelevisitTypeEnum televisitTypeEnum,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                  LocalDateTime startTime,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                  LocalDateTime endTime
                                                      ){
        SortingParamsDto sortingParamsDto = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        TelevisitSearchingParamsDto searchingParamsDto = TelevisitPaginationSupport.getTelevisitSearchingParams(userId,
                televisitStatusEnum, televisitTypeEnum, startTime, endTime);
        TelevisitPaginationDto paginationDto = TelevisitPaginationSupport.getTelevisitPaginationDto(pageNumber, pageSize,
                sortingParamsDto, searchingParamsDto);
        return televisitService.getAllTelevisitsPaginated(paginationDto);
    }
}
