package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.entity.Assistant;
import edu.ib.webapp.user.entity.Doctor;
import edu.ib.webapp.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private LocalDate birthday;

    private String phoneNumber;

    private Long age;

    private String pesel;

    private String userPassword;

    private Boolean isOnline;

    private String address;

    private String town;

    private String postalCode;

    private String voivodeship;

    private String country;

    private Boolean isActive;

    private DoctorResponse doctor;

    private AssistantResponse assistant;

    private Set<Role> roles;
}
