package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Routine;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class RoutineResponse {

    private Integer idRoutine;
    private int creator;
    private String name;
    private int totalTime;
    private int difficulty;
    private Set<SessionResponse> sessions;
    private Set<AccessoryResponse> accessories;

    public RoutineResponse(Routine routine) {
        this.idRoutine = routine.getIdRoutine();
        this.creator = routine.getCreator().getId();
        this.name = routine.getName();
        this.totalTime = routine.getTotalTime();
        this.difficulty = routine.getDifficulty();
        this.sessions = routine.getSessions().stream()
                .map(SessionResponse::new)
                .collect(Collectors.toSet());
        // You will have to make an explicit call on the lazy collection
        // in order to initialize it (common practice is to call .size() for this purpose)
        //noinspection ResultOfMethodCallIgnored
        sessions.size();
        this.accessories = routine.getAccessories().stream()
                .map(AccessoryResponse::new)
                .collect(Collectors.toSet());
        //noinspection ResultOfMethodCallIgnored
        accessories.size();
    }
}
