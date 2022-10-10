package edu.ib.webapp.user.entity;

import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "TELEVISITS")
public class Televisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TelevisitStatusEnum televisitStatusEnum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TelevisitTypeEnum televisitTypeEnum;

    @ManyToMany(mappedBy = "televisits")
    private List<User> users;
}
