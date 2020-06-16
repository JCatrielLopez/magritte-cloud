package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccessoriesResponse {

    private Set<AccessoryResponse> accessories;

    public AccessoriesResponse(Set<AccessoryResponse> accessories) {
        this.accessories = accessories;
    }
}
