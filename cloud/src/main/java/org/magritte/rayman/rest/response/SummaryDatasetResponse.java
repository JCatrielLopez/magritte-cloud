package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class SummaryDatasetResponse extends SummaryResponse{

    private List<DataSetResponse> datasets;
}
