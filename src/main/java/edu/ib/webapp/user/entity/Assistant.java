package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.AssistantSpecializationEnum;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "ASSISTANT")
public class Assistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssistantSpecializationEnum assistantSpecializationEnum;

    @JsonBackReference
    @ToString.Exclude
    @OneToOne(mappedBy = "assistant", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private User user;
}
