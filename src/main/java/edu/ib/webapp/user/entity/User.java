package edu.ib.webapp.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private LocalDate birthday;

    private Long age;

    @NotBlank
    @Column(unique = true)
    @Size(min = 9, max = 12)
    private String phoneNumber;

    @NotBlank
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    @Size(max = 200, min = 6)
    private String userPassword;

    @Size(max = 100)
    private String address;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 100)
    private String voivodeship;

    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String town;

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
            name = "USER_VISITS",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "visit_id")}
    )
    private List<Visit> visits = new ArrayList<>();

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;

//    @OneToMany(mappedBy = "user",
//            fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    private List<Drug> drugs;
}
