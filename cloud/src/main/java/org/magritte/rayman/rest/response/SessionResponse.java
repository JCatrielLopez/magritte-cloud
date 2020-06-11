package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Session;

@Getter
@Setter
@NoArgsConstructor
public class SessionResponse {

    private Integer id;
    private String name;
    private int numberOfSeries;
    private int numberOfRepetitions;
    private int exerciseTime;
    private int breakTime;

    public SessionResponse(Session session) {
        this.id = session.getId();
        this.name = session.getName();
        this.numberOfSeries = session.getNumberOfSeries();
        this.numberOfRepetitions = session.getNumberOfRepetitions();
        this.exerciseTime = session.getExerciseTime();
        this.breakTime = session.getBreakTime();
    }
}
