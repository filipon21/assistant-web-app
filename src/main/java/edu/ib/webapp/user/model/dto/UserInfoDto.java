package edu.ib.webapp.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;

    private String userFirstName;

    private String userLastName;

    private String phoneNumber;

    private String pesel;

}
