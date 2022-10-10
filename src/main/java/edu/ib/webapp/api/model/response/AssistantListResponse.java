package edu.ib.webapp.api.model.response;

import edu.ib.webapp.api.model.dto.AssistantInfoDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssistantListResponse {
    Page<AssistantInfoDto> usersPage;

}
