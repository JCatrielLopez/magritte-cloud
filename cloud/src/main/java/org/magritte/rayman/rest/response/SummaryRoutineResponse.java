package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummaryRoutineResponse extends SummaryResponse{

    private Integer idRoutine;
    private String unit;

}
