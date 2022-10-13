package edu.ib.webapp.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AssistantSearchingParamsDto {

    private String userFirstName;

    private String userLastName;

    private String phoneNumber;

    private Boolean isOnline;

}
