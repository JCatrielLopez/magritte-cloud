package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoutineResponse {

    private Integer idRoutine;
    private String creator;
    private String name;
    private int totalTime;
    private int difficulty;
    private Set<Session> sessions;

    public RoutineResponse(Routine routine) {
        this.idRoutine = routine.getIdRoutine();
        this.creator = routine.getCreator();
        this.name = routine.getName();
        this.totalTime = routine.getTotalTime();
        this.difficulty = routine.getDifficulty();
        this.sessions = routine.getSessions();
    }
}
