package edu.ib.webapp.api.model.dto;

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
public class AssistantPaginationDto {
    @NotNull
    private Integer pageSize;

    @NotNull
    private Integer pageNumber;

    private SortingParamsDto sortingParams;

    private AssistantSearchingParams searchingParams;
}
