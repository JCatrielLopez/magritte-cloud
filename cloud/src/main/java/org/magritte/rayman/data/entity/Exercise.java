package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Exercise {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    // Hay que acomodar segun la base de datos, excercise no existe mas
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "exercise")
    private Set<User> users;
}
