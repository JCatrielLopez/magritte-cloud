package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SummaryPatientResponse extends SummaryResponse{

    private Integer idPatient;
    private String unit;
    private HashSet<SummaryResponse> routines;

}