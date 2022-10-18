package edu.ib.webapp.user.model.dto;

import edu.ib.webapp.user.model.response.AssistantResponse;
import edu.ib.webapp.user.model.response.DoctorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseInfoDto {
    private Long id;

    private String userFirstName;

    private String userLastName;

    private String phoneNumber;

    private DoctorResponse doctor;

    private AssistantResponse assistant;
}
