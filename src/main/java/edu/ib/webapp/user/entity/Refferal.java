package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.RefferalStatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Refferals")
public class Refferal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DoctorSpecializationEnum doctorSpecializationEnum;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private RefferalStatusEnum status;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "visit_id")
    private Visit visit;
}
