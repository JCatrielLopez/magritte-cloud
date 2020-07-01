package org.magritte.rayman.rest.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SummaryResponse {

    private double avg;
    private double min;
    private double max;
    private double variance;
    private List<DataSetResponse> datasets;

}


