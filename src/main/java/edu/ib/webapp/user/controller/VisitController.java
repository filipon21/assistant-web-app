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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * Klasa do obsługi funkcjonalności związanej z wizytami (kontroler Springowy)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visit")
public class VisitController {

    private final VisitService visitService;

    /**
     * Metoda służąca do obsługi zaptyania dot. tworzenia telewizyt przez pacjenta w czasie rzeczywistym
     * @param visitRequest - dane wizyty
     * @param userId - id pacjenta
     * @param hostId - id pracownika
     * @param authentication - dane ze springa o aktualnym użytkowniku
     * @return zwraca dane o utworzonej wizycie
     */
    @PostMapping("/{userId}/{hostId}")
    public VisitResponse createTeleVisit(@RequestBody VisitRequest visitRequest, @PathVariable Long userId,
                                     @PathVariable Long hostId, Authentication authentication) {
        return visitService.createTeleVisit(visitRequest, userId, hostId, authentication.getName());
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. tworzenia wizyty przez pracownika
     * @param visitRequest - dane dot. wizyty
     * @param hostId - id pracownika (bazodanowe)
     * @return dane dot. wizyty
     */
    @PostMapping("/{hostId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    public VisitResponse createVisit(@RequestBody VisitRequest visitRequest, @PathVariable Long hostId) {
        return visitService.createVisit(visitRequest, hostId);
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. edycji danej wizyty
     * @param id - id wizyty
     * @param visitRequest - dane do edyycji wizyty
     * @return dane nowej wizyty
     */
    @PatchMapping("/{id}")
    public VisitResponse updateVisit(@PathVariable Long id, @Valid @RequestBody VisitRequest visitRequest) {
        return visitService.updateVisit(id, visitRequest);
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. dodawania użytkownika do wizyty
     * @param id - id wizyty
     * @param userId - id użytkownika, którego chcemy dodać do wizyty
     * @param refferalId - ew. id skierowanie na wizytę
     * @return zwraca dane wizyty
     */
    @PatchMapping("/{id}/user/{userId}")
    public VisitResponse addUserToVisit(@PathVariable Long id, @PathVariable Long userId,
                                        @RequestParam(required = false) Long refferalId) {
        return visitService.addUserToVisit(refferalId, userId, id);
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. usunięcia użytkownika z wizyty
     * @param id - id wizyty
     * @param userId - id użytkownika, którego chcemy usunac
     * @param refferalId - id skierowanie (opcjonalne)
     * @return dane wizyty
     */
    @DeleteMapping("/{id}/user/{userId}")
    public VisitResponse deleteUserFromVisit(@PathVariable Long id, @PathVariable Long userId,
                                        @RequestParam(required = false) Long refferalId) {
        return visitService.deleteUserFromVisit(refferalId, userId, id);
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. znalezienia aktualnych telewizyt użytkownika
     * @param userId - id użytkownika
     * @return lista telewizyt
     */
    @GetMapping("/online/{userId}")
    public VisitResponse getUserVisits(@PathVariable Long userId){
        return visitService.getUserTeleVisit(userId);
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. znalezienia aktualnych wizyt użytkownika, które mnaja status STARTED
     * @param userId - id użytkownika
     * @return lista znaleizonych wizyt
     */
    @GetMapping("/started/{userId}")
    public VisitResponse getUserStartedVisit(@PathVariable Long userId){
        return visitService.getUserStartedVisit(userId);
    }

    /**
     * Metoda służąca do obsługi zaptyania dot. znalezienia danej wizyty
     * @param id - id wizyty
     * @return dane wizyty
     */
    @GetMapping("/{id}")
    public VisitResponse getVisit(@PathVariable Long id){
        return visitService.getVisit(id);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. zwrócenia listy wszystkich wolnych wizyt (filtracja, paginacja)
     * @param pageNumber - numer aktualnej strony
     * @param pageSize - aktualny rozmiar strony
     * @param sortParameter - parametr po którym jest sortowanie
     * @param sortDirection - kierunek sortowania (rosnoący (asc), malejacy (desc))
     * @param visitStatusEnum - status wizyty
     * @param visitTypeEnum - typ wizyty
     * @param address - adres wizyty
     * @param doctorId - id pracownika
     * @param startTime - data rozpoczęcia
     * @param endTime - data zakończenia
     * @return zwraca listę wolnych wizyt po ew. filtracji
     */
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

    /**
     * Metoda służąca do obsługi zapytania dot. zwrócenia listy wszystkich nadchodzących wizyt użytkownika (filtracja, paginacja)
     * @param authentication - dane aktualnego użytkownika
     * @param pageNumber - numer aktualnej strony
     * @param pageSize - aktualny rozmiar strony
     * @param sortParameter - parametr po którym jest sortowanie
     * @param sortDirection - kierunek sortowania (rosnoący (asc), malejacy (desc))
     * @param visitStatusEnum - status wizyty
     * @param visitTypeEnum - typ wizyty
     * @param address - adres wizyty
     * @param startTime - data rozpoczęcia
     * @param endTime - data zakończenia
     * @return zwraca listę nadchodzących wizyt po ew. filtracji
     */
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

    /**
     * Metoda służąca do obsługi zapytania dot. zwrócenia listy wszystkich historii wizyt użytkownika (filtracja, paginacja)
     * @param authentication - dane aktualnego użytkownika
     * @param pageNumber - numer aktualnej strony
     * @param pageSize - aktualny rozmiar strony
     * @param sortParameter - parametr po którym jest sortowanie
     * @param sortDirection - kierunek sortowania (rosnoący (asc), malejacy (desc))
     * @param visitStatusEnum - status wizyty
     * @param visitTypeEnum - typ wizyty
     * @param address - adres wizyty
     * @param startTime - data rozpoczęcia
     * @param endTime - data zakończenia
     * @return zwraca listę historii wizyt po ew. filtracji
     */
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
                                                      @RequestParam(required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              LocalDateTime startTime,
                                                      @RequestParam(required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
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
