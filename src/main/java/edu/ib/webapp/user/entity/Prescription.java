package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileCode;

    @NotBlank
    private String code;

    @NotBlank
    private String type;

    @JsonBackReference
    @ToString.Exclude
    @OneToOne(mappedBy = "prescription", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Visit visit;
}
