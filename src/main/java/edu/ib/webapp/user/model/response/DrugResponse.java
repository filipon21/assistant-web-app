package edu.ib.webapp.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrugResponse {
    private Long id;

    private String drugName;

    private String dosage;

    private String description;
}
