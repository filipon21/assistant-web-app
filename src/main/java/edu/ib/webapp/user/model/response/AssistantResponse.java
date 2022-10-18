package edu.ib.webapp.user.model.response;

import edu.ib.webapp.user.enums.AssistantSpecializationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssistantResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private AssistantSpecializationEnum assistantSpecializationEnum;
}
