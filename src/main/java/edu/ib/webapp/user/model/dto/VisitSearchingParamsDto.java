package edu.ib.webapp.user.model.dto;

import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VisitSearchingParamsDto {

    private Long userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private Long doctorId;

    @Enumerated(EnumType.STRING)
    private VisitTypeEnum visitTypeEnum;

    @Enumerated(EnumType.STRING)
    private VisitStatusEnum visitStatusEnum;

}
