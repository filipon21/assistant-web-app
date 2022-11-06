package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String description;

    private Long refferalId;

    @OneToMany(mappedBy = "visit",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Refferal> refferals = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "exemption_id")
    private Exemption exemption;

    @ManyToMany(mappedBy = "visits")
    private List<User> users = new ArrayList<>();
}
