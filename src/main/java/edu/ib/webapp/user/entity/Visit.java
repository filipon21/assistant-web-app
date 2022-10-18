package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VisitStatusEnum visitStatusEnum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VisitTypeEnum visitTypeEnum;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private String recommendation;

    @JsonBackReference
    @ToString.Exclude
    @OneToOne(mappedBy = "visit", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Prescription prescription;

    private String exemption;

    @ManyToMany(mappedBy = "visits")
    private List<User> users;
}
