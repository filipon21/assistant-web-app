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
@Table(name = "drugs")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    private String drugName;

    @NotBlank
    private String dosage;

    private String description;

//    @JsonBackReference
//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

}
