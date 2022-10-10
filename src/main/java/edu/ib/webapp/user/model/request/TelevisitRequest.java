package edu.ib.webapp.user.model.request;

import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
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
public class TelevisitRequest {

    @Enumerated(EnumType.STRING)
    private TelevisitStatusEnum televisitStatusEnum;

    @Enumerated(EnumType.STRING)
    private TelevisitTypeEnum televisitTypeEnum;

}
