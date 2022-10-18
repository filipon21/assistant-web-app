package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private Long id;

    @Enumerated(EnumType.STRING)
    private DoctorSpecializationEnum doctorSpecializationEnum;
}
