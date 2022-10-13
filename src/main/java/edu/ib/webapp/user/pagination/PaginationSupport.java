package edu.ib.webapp.user.pagination;


import edu.ib.webapp.user.model.dto.AssistantPaginationDto;
import edu.ib.webapp.user.model.dto.AssistantSearchingParamsDto;
import edu.ib.webapp.common.enums.SortingDirectionEnum;
import edu.ib.webapp.common.pagination.SortingParamsDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PaginationSupport {

    public static PageRequest getPageRequest(AssistantPaginationDto paginationDto) {
        SortingParamsDto sortingParams = paginationDto.getSortingParams();
        String sortingDirection = null;
        String sortingParameter = null;
        if (sortingParams != null) {
            sortingDirection = sortingParams.getSortDirection();
            sortingParameter = sortingParams.getSortParameter();
        }
        return getAssistantPageRequest(paginationDto.getPageNumber(), paginationDto.getPageSize(),
                sortingDirection, sortingParameter);
    }

    public PageRequest getAssistantPageRequest(Integer page, Integer size, String sortDir, String sortParam) {
        return (StringUtils.isEmpty(sortDir) || StringUtils.isEmpty(sortParam)) ?
                PageRequest.of(page, size, prepareDefaultTemplateSort()) :
                PageRequest.of(page, size, prepareSortWithIgnoreCase(sortParam, sortDir));
    }

    private Sort prepareDefaultTemplateSort() {
        return Sort.by(Sort.Order.desc("isOnline"));
    }

    private Sort prepareSortWithIgnoreCase(String sortParam, String sortDir) {
        Sort.Direction direction = StringUtils.equalsIgnoreCase(sortDir, SortingDirectionEnum.DEFAULT_SORT_DIRECTION.getDirection()) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(new Sort.Order(direction, sortParam).ignoreCase());
    }

    public AssistantPaginationDto getAssistantPaginationDto(Integer pageNumber, Integer pageSize, SortingParamsDto sortingParams,
                                                            AssistantSearchingParamsDto searchingParams) {
        return AssistantPaginationDto.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortingParams(sortingParams)
                .searchingParams(searchingParams)
                .build();
    }

    public AssistantSearchingParamsDto getAssistantSearchingParams(String userFirstName, String userLastName, String phoneNumber,
                                                                   Boolean isOnline) {
        return AssistantSearchingParamsDto.builder()
                .userFirstName(userFirstName)
                .userLastName(userLastName)
                .isOnline(isOnline)
                .phoneNumber(phoneNumber)
                .build();
    }

    public SortingParamsDto getSortingParams(String sortParameter, String sortDirection) {
        return SortingParamsDto.builder()
                .sortDirection(sortDirection)
                .sortParameter(sortParameter)
                .build();
    }

}
