package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelevisitResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private TelevisitStatusEnum televisitStatusEnum;

    @Enumerated(EnumType.STRING)
    private TelevisitTypeEnum televisitTypeEnum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private UserResponse user;
}
