package edu.ib.webapp.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrugRequest {
    @NotBlank
    private String drugName;

    @NotBlank
    private String dosage;

    private String description;

}
