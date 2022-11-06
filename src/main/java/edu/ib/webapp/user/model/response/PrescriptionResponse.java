package edu.ib.webapp.user.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {

    private Long id;

    private String code;

    private String fileCode;

    private String type;

}