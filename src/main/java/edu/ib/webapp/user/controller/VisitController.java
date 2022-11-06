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

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('USER', 'ASSISTANT')")
@RequestMapping("/api/visit")
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/{userId}/{hostId}")
    public VisitResponse createTeleVisit(@RequestBody VisitRequest visitRequest, @PathVariable Long userId,
                                     @PathVariable Long hostId, Authentication authentication) {
        return visitService.createTeleVisit(visitRequest, userId, hostId, authentication.getName());
    }

    @PostMapping("/{hostId}")
    public VisitResponse createVisit(@RequestBody VisitRequest visitRequest, @PathVariable Long hostId) {
        return visitService.createVisit(visitRequest, hostId);
    }

    @PatchMapping("/{id}")
    public VisitResponse updateVisit(@PathVariable Long id, @Valid @RequestBody VisitRequest visitRequest) {
        return visitService.updateVisit(id, visitRequest);
    }

    @PatchMapping("/{id}/user/{userId}")
    public VisitResponse addUserToVisit(@PathVariable Long id, @PathVariable Long userId,
                                        @RequestParam(required = false) Long refferalId) {
        return visitService.addUserToVisit(refferalId, userId, id);
    }

    @DeleteMapping("/{id}/user/{userId}")
    public VisitResponse deleteUserFromVisit(@PathVariable Long id, @PathVariable Long userId,
                                        @RequestParam(required = false) Long refferalId) {
        return visitService.deleteUserFromVisit(refferalId, userId, id);
    }

    @GetMapping("/online/{userId}")
    public VisitResponse getUserVisits(@PathVariable Long userId){
        return visitService.getUserTeleVisit(userId);
    }

    @GetMapping("/{id}")
    public VisitResponse getVisit(@PathVariable Long id){
        return visitService.getVisit(id);
    }

    @GetMapping("/list/free")
    public VisitListResponse getAllFreeVisits(@RequestParam Integer pageNumber,
                                                  @RequestParam Integer pageSize,
                                                  @RequestParam(required = false) String sortParameter,
                                                  @RequestParam(required = false) String sortDirection,
                                                  @RequestParam(required = false) VisitStatusEnum visitStatusEnum,
                                                  @RequestParam(required = false) VisitTypeEnum visitTypeEnum,
                                                  @RequestParam(required = false) String address,
                                                  @RequestParam(required = false) Long doctorId,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime startTime,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime endTime
    ){
        SortingParamsDto sortingParamsDto = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        VisitSearchingParamsDto searchingParamsDto = VisitPaginationSupport.getVisitSearchingParams(
                visitStatusEnum, visitTypeEnum, startTime, endTime, address, doctorId);
        VisitPaginationDto paginationDto = VisitPaginationSupport.getVisitPaginationDto(pageNumber, pageSize,
                sortingParamsDto, searchingParamsDto);
        return visitService.getAllFreeVisitsPaginated(paginationDto);
    }

    @GetMapping("/list/upcoming")
    public VisitListResponse getAllUserUpcomingVisits(Authentication authentication,
                                                      @RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestParam(required = false) String sortParameter,
                                                     @RequestParam(required = false) String sortDirection,
                                                     @RequestParam Long userId,
                                                     @RequestParam(required = false) VisitStatusEnum visitStatusEnum,
                                                     @RequestParam(required = false) VisitTypeEnum visitTypeEnum,
                                                     @RequestParam(required = false) String address,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime startTime,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime endTime
    ){
        SortingParamsDto sortingParamsDto = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        VisitSearchingParamsDto searchingParamsDto = VisitPaginationSupport.getVisitSearchingParams(userId,
                visitStatusEnum, visitTypeEnum, startTime, endTime, address);
        VisitPaginationDto paginationDto = VisitPaginationSupport.getVisitPaginationDto(pageNumber, pageSize,
                sortingParamsDto, searchingParamsDto);
        return visitService.getAllUpcomingVisitsPaginated(paginationDto, authentication.getName());
    }

    @GetMapping("/list/history")
    public VisitListResponse getAllUserHistoryVisits(Authentication authentication,
                                                      @RequestParam Integer pageNumber,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam(required = false) String sortParameter,
                                                      @RequestParam(required = false) String sortDirection,
                                                      @RequestParam Long userId,
                                                      @RequestParam(required = false) VisitStatusEnum visitStatusEnum,
                                                      @RequestParam(required = false) VisitTypeEnum visitTypeEnum,
                                                      @RequestParam(required = false) String address,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime startTime,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime endTime
    ){
        SortingParamsDto sortingParamsDto = PaginationSupport.getSortingParams(sortParameter, sortDirection);
        VisitSearchingParamsDto searchingParamsDto = VisitPaginationSupport.getVisitSearchingParams(userId,
                visitStatusEnum, visitTypeEnum, startTime, endTime, address);
        VisitPaginationDto paginationDto = VisitPaginationSupport.getVisitPaginationDto(pageNumber, pageSize,
                sortingParamsDto, searchingParamsDto);
        return visitService.getAllHistoryVisitsPaginated(paginationDto, authentication.getName());
    }
}
