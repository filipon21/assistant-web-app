package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "doctor",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<User> user;

}
