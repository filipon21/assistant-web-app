package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String userName;

    private String userFirstName;

    private String userLastName;

    private String phoneNumber;

    private String pesel;

    private String userPassword;

    private Boolean isOnline;

    private String address;

    private String postalCode;

    private String voivodeship;

    private String country;

    private Boolean isActive;

    private Set<Role> roles;
}
