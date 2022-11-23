package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.entity.Prescription;
import edu.ib.webapp.user.entity.Refferal;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.model.request.PrescriptionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private VisitStatusEnum visitStatusEnum;

    @Enumerated(EnumType.STRING)
    private VisitTypeEnum visitTypeEnum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private PrescriptionResponse prescription;

    private ExemptionResponse exemption;

    private List<Refferal> refferals;

    private String recommendation;

    private Long refferalId;

    private String chatLink;


    private String description;

    private List<UserResponse> users;
}
