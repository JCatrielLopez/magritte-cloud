package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {

    @Size(min = 3, max = 15)
    @NotNull
    private String name;

    @Min(1)
    @Max(10)
    @NotNull
    private int numberOfSeries;

    @Min(1)
    @Max(200)
    @NotNull
    private int numberOfRepetitions;

    @NotNull
    private int exerciseTime;

    @NotNull
    private int breakTime;

    public Session toNewEntity(Routine routine) {
        return new Session(routine, getName(), getNumberOfSeries(),
                getNumberOfRepetitions(), getExerciseTime(), getBreakTime());
    }
}
