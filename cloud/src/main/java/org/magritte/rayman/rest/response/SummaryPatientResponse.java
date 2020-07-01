package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SummaryPatientResponse extends SummaryResponse{

    private Integer idPatient;
    private String unit;
    private List<DataSetResponse> datasets;

}