package edu.ib.webapp.user.model.dto;

import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.model.response.UserResponse;
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
public class VisitInfoDto {

    private Long id;

    @Enumerated(EnumType.STRING)
    private VisitStatusEnum visitStatusEnum;

    @Enumerated(EnumType.STRING)
    private VisitTypeEnum visitTypeEnum;

    private String address;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<UserResponseInfoDto> users;
}
