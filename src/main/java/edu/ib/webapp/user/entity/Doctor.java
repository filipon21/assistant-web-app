package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "DOCTORS")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DoctorSpecializationEnum doctorSpecializationEnum;

    @JsonBackReference
    @ToString.Exclude
    @OneToOne(mappedBy = "doctor", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private User user;
}
