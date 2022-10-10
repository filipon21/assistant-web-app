package edu.ib.webapp.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank
    @Email
    @Column(unique = true)
    @Size(max = 100)
    private String userName;

    @NotBlank
    @Size(max = 100)
    private String userFirstName;

    @NotBlank
    @Size(max = 100)
    private String userLastName;

    @NotBlank
    @Column(unique = true)
    @Size(min = 9, max = 12)
    private String phoneNumber;

    @NotBlank
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    @Size(max = 100)
    private String address;

    @NotBlank
    @Size(max = 10)
    private String postalCode;

    @NotBlank
    @Size(max = 100)
    private String voivodeship;

    @NotBlank
    @Size(max = 100)
    private String country;

}
