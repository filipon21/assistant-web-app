package edu.ib.webapp.user.pagination;

import edu.ib.webapp.common.enums.SortingDirectionEnum;
import edu.ib.webapp.common.pagination.SortingParamsDto;
import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
import edu.ib.webapp.user.model.dto.TelevisitPaginationDto;
import edu.ib.webapp.user.model.dto.TelevisitSearchingParamsDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@UtilityClass
public class TelevisitPaginationSupport {

    public static PageRequest getPageRequest(TelevisitPaginationDto paginationDto) {
        SortingParamsDto sortingParams = paginationDto.getSortingParams();
        String sortingDirection = null;
        String sortingParameter = null;
        if (sortingParams != null) {
            sortingDirection = sortingParams.getSortDirection();
            sortingParameter = sortingParams.getSortParameter();
        }
        return getTelevisitPageRequest(paginationDto.getPageNumber(), paginationDto.getPageSize(),
                sortingDirection, sortingParameter);
    }

    private static PageRequest getTelevisitPageRequest(Integer pageNumber, Integer pageSize, String sortingDirection,
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

    public TelevisitPaginationDto getTelevisitPaginationDto(Integer pageNumber, Integer pageSize, SortingParamsDto sortingParams,
                                                          TelevisitSearchingParamsDto searchingParams) {
        return TelevisitPaginationDto.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortingParams(sortingParams)
                .searchingParams(searchingParams)
                .build();
    }

    public TelevisitSearchingParamsDto getTelevisitSearchingParams(Long userId, TelevisitStatusEnum status, TelevisitTypeEnum type,
                                                                   LocalDateTime startTime, LocalDateTime endTime) {
        return TelevisitSearchingParamsDto.builder()
                .userId(userId)
                .televisitStatusEnum(status)
                .televisitTypeEnum(type)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public SortingParamsDto getSortingParams(String sortParameter, String sortDirection) {
        return SortingParamsDto.builder()
                .sortDirection(sortDirection)
                .sortParameter(sortParameter)
                .build();
    }
}
