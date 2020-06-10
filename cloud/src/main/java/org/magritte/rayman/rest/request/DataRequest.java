package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.magritte.rayman.data.entity.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataRequest {

    @Size(min = 3, max = 20)
    @NotNull
    private String dataType;

    public Data toNewEnitty() {
        return new Data(getDataType());
    }
}
