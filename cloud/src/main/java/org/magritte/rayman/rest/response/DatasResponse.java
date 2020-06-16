package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DatasResponse {

    private Set<DataResponse> data;

    public DatasResponse(Set<DataResponse> data) {
        this.data = data;
        //noinspection ResultOfMethodCallIgnored
        this.data.size();
    }
}
