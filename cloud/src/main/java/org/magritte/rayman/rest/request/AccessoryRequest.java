package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.entity.Data;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        return new Accessory(getName(), getCollect());
    }

    @org.jetbrains.annotations.NotNull
    public Set<Data> getCollect() {
        return getData().stream()
                .map(DataRequest::toNewEnitty)
                .collect(Collectors.toSet());
    }
}
