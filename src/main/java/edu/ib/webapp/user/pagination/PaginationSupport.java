package edu.ib.webapp.user.pagination;


import edu.ib.webapp.user.model.dto.AssistantPaginationDto;
import edu.ib.webapp.user.model.dto.AssistantSearchingParamsDto;
import edu.ib.webapp.common.enums.SortingDirectionEnum;
import edu.ib.webapp.common.pagination.SortingParamsDto;
import edu.ib.webapp.user.model.dto.UserPaginationDto;
import edu.ib.webapp.user.model.dto.UserSearchingParamsDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Klasa "wspomagająca" do paginacji
 */
@UtilityClass
public class PaginationSupport {

    /**
     * Metoda służąca do utworzenia PageRequesta dla listy asystentów
     * @param paginationDto - dane do paginacji
     * @return PageRequest(numer strony, liczba elementów na stronie, kierunek sortowania, parametr po którym jest sortowanie)
     */
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

    /**
     * Metoda służąca do utworzenia PageRequesta dla listy użytkowników
     * @param paginationDto - dane do paginacji
     * @return PageRequest(numer strony, liczba elementów na stronie, kierunek sortowania, parametr po którym jest sortowanie)
     */
    public static PageRequest getPageRequest(UserPaginationDto paginationDto) {
        SortingParamsDto sortingParams = paginationDto.getSortingParams();
        String sortingDirection = null;
        String sortingParameter = null;
        if (sortingParams != null) {
            sortingDirection = sortingParams.getSortDirection();
            sortingParameter = sortingParams.getSortParameter();
        }
        return getUserPageRequest(paginationDto.getPageNumber(), paginationDto.getPageSize(),
                sortingDirection, sortingParameter);
    }

    public PageRequest getAssistantPageRequest(Integer page, Integer size, String sortDir, String sortParam) {
        return (StringUtils.isEmpty(sortDir) || StringUtils.isEmpty(sortParam)) ?
                PageRequest.of(page, size, prepareDefaultSort()) :
                PageRequest.of(page, size, prepareSortWithIgnoreCase(sortParam, sortDir));
    }

    public PageRequest getUserPageRequest(Integer page, Integer size, String sortDir, String sortParam) {
        return (StringUtils.isEmpty(sortDir) || StringUtils.isEmpty(sortParam)) ?
                PageRequest.of(page, size, prepareDefaultSortByLastName()) :
                PageRequest.of(page, size, prepareSortWithIgnoreCase(sortParam, sortDir));
    }

    private Sort prepareDefaultSort() {
        return Sort.by(Sort.Order.desc("isOnline"));
    }

    private Sort prepareDefaultSortByLastName() {
        return Sort.by(Sort.Order.desc("userLastName"));
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

    public UserPaginationDto getUserPaginationDto(Integer pageNumber, Integer pageSize, SortingParamsDto sortingParams,
                                                            UserSearchingParamsDto searchingParams) {
        return UserPaginationDto.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortingParams(sortingParams)
                .searchingParams(searchingParams)
                .build();
    }

    public UserSearchingParamsDto getUserSearchingParams(String userFirstName, String userLastName, String phoneNumber,
                                                                   String pesel) {
        return UserSearchingParamsDto.builder()
                .userFirstName(userFirstName)
                .userLastName(userLastName)
                .pesel(pesel)
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
