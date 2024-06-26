package edu.ib.webapp.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionRequest {

    private String code;

    private String fileCode;

    private String type;

}
