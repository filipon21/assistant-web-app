package edu.ib.webapp.user.model.dto;

import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
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
public class TelevisitInfoDto {

    private Long id;

    @Enumerated(EnumType.STRING)
    private TelevisitStatusEnum televisitStatusEnum;

    @Enumerated(EnumType.STRING)
    private TelevisitTypeEnum televisitTypeEnum;

    private LocalDateTime startTime;

    private List<UserResponse> users;
}
