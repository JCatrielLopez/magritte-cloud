package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Accessory;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AccessoryResponse {

    private Integer idAccessory;
    private String name;
    private Set<DataResponse> data;

    public AccessoryResponse(Accessory accessory) {
        this.idAccessory = accessory.getIdAccessory();
        this.name = accessory.getName();
        this.data = accessory.getData().stream()
                .map(DataResponse::new)
                .collect(Collectors.toSet());
        //noinspection ResultOfMethodCallIgnored
        data.size();
    }
}
