package edu.ib.webapp.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    @Email
    @Column(unique = true)
    private String userName;

    @NotBlank
    private String userFirstName;

    @NotBlank
    private String userLastName;

    @NotBlank
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    private String userPassword;

    @NotBlank
    @Column(unique = true)
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String voivodeship;

    @NotBlank
    private String country;

    @NotNull
    private Boolean isActive;
}
