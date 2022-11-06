package edu.ib.webapp.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserSearchingParamsDto {

    private String userFirstName;

    private String userLastName;

    private String phoneNumber;

    private String pesel;

}
