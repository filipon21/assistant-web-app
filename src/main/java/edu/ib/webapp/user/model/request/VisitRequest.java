package edu.ib.webapp.user.model.request;

import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitRequest {

    @Enumerated(EnumType.STRING)
    private VisitStatusEnum visitStatusEnum;

    @Enumerated(EnumType.STRING)
    private VisitTypeEnum visitTypeEnum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private String recommendation;

    private String description;

    private Long refferalId;

    private String chatLink;

    private PrescriptionRequest prescription;

//    private ExemptionRequest exemption;

}
