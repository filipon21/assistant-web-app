package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.RefferalStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefferalResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private DoctorSpecializationEnum doctorSpecializationEnum;

    private LocalDateTime endTime;

    private RefferalStatusEnum status;

}
