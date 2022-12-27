package edu.ib.webapp.user.pagination;

import edu.ib.webapp.common.enums.SortingDirectionEnum;
import edu.ib.webapp.common.pagination.SortingParamsDto;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.model.dto.VisitPaginationDto;
import edu.ib.webapp.user.model.dto.VisitSearchingParamsDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

/**
 * Klasa "wspomagająca" do paginacji
 */
@UtilityClass
public class VisitPaginationSupport {

    public static PageRequest getPageRequest(VisitPaginationDto paginationDto) {
        SortingParamsDto sortingParams = paginationDto.getSortingParams();
        String sortingDirection = null;
        String sortingParameter = null;
        if (sortingParams != null) {
            sortingDirection = sortingParams.getSortDirection();
            sortingParameter = sortingParams.getSortParameter();
        }
        return getVisitPageRequest(paginationDto.getPageNumber(), paginationDto.getPageSize(),
                sortingDirection, sortingParameter);
    }

    private static PageRequest getVisitPageRequest(Integer pageNumber, Integer pageSize, String sortingDirection,
                                                   String sortingParameter) {
        return (StringUtils.isEmpty(sortingDirection) || StringUtils.isEmpty(sortingParameter)) ?
                PageRequest.of(pageNumber, pageSize, prepareDefaultTelevisitSort()) :
                PageRequest.of(pageNumber, pageSize, prepareSortWithIgnoreCase(sortingParameter, sortingDirection));

    }

    private static Sort prepareDefaultTelevisitSort() {
        return Sort.by(Sort.Order.asc("startTime"));
    }

    private static Sort prepareSortWithIgnoreCase(String sortParam, String sortDir) {
        Sort.Direction direction = StringUtils.equalsIgnoreCase(sortDir, SortingDirectionEnum.DEFAULT_SORT_DIRECTION.getDirection()) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(new Sort.Order(direction, sortParam).ignoreCase());
    }

    public VisitPaginationDto getVisitPaginationDto(Integer pageNumber, Integer pageSize, SortingParamsDto sortingParams,
                                                    VisitSearchingParamsDto searchingParams) {
        return VisitPaginationDto.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortingParams(sortingParams)
                .searchingParams(searchingParams)
                .build();
    }

    public VisitSearchingParamsDto getVisitSearchingParams(Long userId, VisitStatusEnum status, VisitTypeEnum type,
                                                           LocalDateTime startTime, LocalDateTime endTime, String address) {
        return VisitSearchingParamsDto.builder()
                .userId(userId)
                .visitStatusEnum(status)
                .visitTypeEnum(type)
                .startTime(startTime)
                .endTime(endTime)
                .address(address)
                .build();
    }

    public VisitSearchingParamsDto getVisitSearchingParams(VisitStatusEnum status, VisitTypeEnum type,
                                                           LocalDateTime startTime, LocalDateTime endTime, String address) {
        return VisitSearchingParamsDto.builder()
                .visitStatusEnum(status)
                .visitTypeEnum(type)
                .startTime(startTime)
                .endTime(endTime)
                .address(address)
                .build();
    }

    public VisitSearchingParamsDto getVisitSearchingParams(VisitStatusEnum status, VisitTypeEnum type,
                                                           LocalDateTime startTime, LocalDateTime endTime,
                                                           String address, Long doctorId) {
        return VisitSearchingParamsDto.builder()
                .visitStatusEnum(status)
                .doctorId(doctorId)
                .visitTypeEnum(type)
                .startTime(startTime)
                .endTime(endTime)
                .address(address)
                .build();
    }

    public SortingParamsDto getSortingParams(String sortParameter, String sortDirection) {
        return SortingParamsDto.builder()
                .sortDirection(sortDirection)
                .sortParameter(sortParameter)
                .build();
    }
}
