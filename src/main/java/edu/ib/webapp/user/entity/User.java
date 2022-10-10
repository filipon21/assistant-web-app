package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true)
    @Size(max = 100)
    private String userName;

    @NotBlank
    @Size(max = 100)
    private String userFirstName;

    @NotBlank
    @Size(max = 100)
    private String userLastName;

    @NotBlank
    @Column(unique = true)
    @Size(min = 9, max = 12)
    private String phoneNumber;

    @NotBlank
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    @Size(max = 200)
    private String userPassword;

    @NotBlank
    @Size(max = 100)
    private String address;

    @NotBlank
    @Size(max = 10)
    private String postalCode;

    @NotBlank
    @Size(max = 100)
    private String voivodeship;

    @NotBlank
    @Size(max = 100)
    private String country;

    //@NotNull
    private Boolean isOnline;

    @NotNull
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private Set<Role> roles;

    @JsonManagedReference
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_TELEVISITS",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "televisit_id")}
    )
    private List<Televisit> televisits;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;
}
