package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Accessory {

    public static final String ID = "idAccessory";
    public static final String NAME_TABLE = "accessories";
    public static final String NAME_TABLE_MANY_TO_MANY_ROUTINE = "routineAccessory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer idAccessory;

    @ToString.Include
    private String name;

    @JoinTable(name = NAME_TABLE_MANY_TO_MANY_ROUTINE,
            joinColumns = @JoinColumn(referencedColumnName = Accessory.ID),
            inverseJoinColumns = @JoinColumn(referencedColumnName = Routine.ID)
    )
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Routine> routine;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Data> data;

}
