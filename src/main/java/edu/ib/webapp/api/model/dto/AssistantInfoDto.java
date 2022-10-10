package edu.ib.webapp.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssistantInfoDto {

    private Long id;

    private String userFirstName;

    private String userLastName;

    private String phoneNumber;

    private Boolean isOnline;

}
