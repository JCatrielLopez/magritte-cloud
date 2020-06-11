package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.entity.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryRequest {

    @Size(min = 3, max = 15)
    @NotNull
    private String name;

    @NotNull
    private Set<DataRequest> data;

    public Accessory toNewEntity() {
        System.out.println(getCollect().toString());
        return new Accessory(getName(), new HashSet<>());
    }

    @org.jetbrains.annotations.NotNull
    public Set<Data> getCollect() {

        /*
            Accessory a = this.toNewEntity();
            for (DataRequest dr : data) {
                Data d = dr.toNewEntity();
                Optional<Data> optionalData = dataRepository.findByName(d.getName());
                if (!optionalData.isPresent()) {
                    d.add(a);
                    a.add(d);
                    dataRepository.save(d);
                } else {
                    Data obtainData = optionalData.get();
                    obtainData.add(a);
                    a.add(obtainData);
                }
            }
            accessoryRepository.save(a);

          for each (Datareq d : conjunto){
          	data = new Data (d);
          	if not exists datarepo(d) //find by name
          		data.setAccesorio(accesorio)
          		accesorio.setData(data)
          		datarepository.save(data)
          	else
          		modifico conjunto del data existente
          		accesorio.setData(dataexistente)
          	accesoryRepository.save(accesorio);
          */

        Set<Data> set = new HashSet<>();
        for (DataRequest dataRequest : getData()) {
            Data toNewEntity = dataRequest.toNewEntity();
            set.add(toNewEntity);
        }
        return set;
    }
}
