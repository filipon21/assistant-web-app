package edu.ib.webapp.user.model.dto;

import edu.ib.webapp.common.pagination.SortingParamsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VisitPaginationDto {

    @NotNull
    private Integer pageSize;

    @NotNull
    private Integer pageNumber;

    private SortingParamsDto sortingParams;

    private VisitSearchingParamsDto searchingParams;

}
