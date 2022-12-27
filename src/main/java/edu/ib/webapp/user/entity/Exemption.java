package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Klasa służąca do utworzenia encji bazodanowej zwolnień
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Exemptions")
public class Exemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private LocalDateTime startTime;

    @JsonBackReference
    @ToString.Exclude
    @OneToOne(mappedBy = "exemption", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Visit visit;

}
