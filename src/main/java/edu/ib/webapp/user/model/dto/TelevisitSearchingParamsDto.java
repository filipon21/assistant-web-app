package edu.ib.webapp.user.model.dto;

import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
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
public class TelevisitSearchingParamsDto {

    private Long userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private TelevisitTypeEnum televisitTypeEnum;

    @Enumerated(EnumType.STRING)
    private TelevisitStatusEnum televisitStatusEnum;

}
