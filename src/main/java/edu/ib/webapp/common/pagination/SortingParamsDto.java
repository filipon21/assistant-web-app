package edu.ib.webapp.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SortingParamsDto {

    private String sortDirection;

    private String sortParameter;



}
