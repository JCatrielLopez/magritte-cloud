package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Data {

    public static final String NAME_TABLE = "data";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "iddata")
    @EqualsAndHashCode.Include
    private Integer idData;

    @ToString.Include
    @Column(name = "datatype")
    private String dataType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = NAME_TABLE)
    private Set<Accessory> accessories;

    public Data(String dataType) {
        this.dataType = dataType;
    }
}
