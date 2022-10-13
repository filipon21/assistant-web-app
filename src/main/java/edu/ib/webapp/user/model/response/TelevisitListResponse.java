package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.model.dto.TelevisitInfoDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelevisitListResponse {
    Page<TelevisitInfoDto> televisitPage;
}
