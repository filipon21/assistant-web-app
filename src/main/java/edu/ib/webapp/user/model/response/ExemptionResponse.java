package edu.ib.webapp.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExemptionResponse {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
