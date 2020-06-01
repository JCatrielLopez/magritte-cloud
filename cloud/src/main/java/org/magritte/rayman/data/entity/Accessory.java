package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer idAccessory;

    @Size(max = 15)
    @ToString.Include
    private String name;

//    @JoinTable(name = "uses",
//            joinColumns = @JoinColumn(referencedColumnName = "idAccesory"),
//            inverseJoinColumns = @JoinColumn(referencedColumnName = "idRoutine")
//    )
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "accessory")
    private Set<Routine> routines;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Data> data;

}
