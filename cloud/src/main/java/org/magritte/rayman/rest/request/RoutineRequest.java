package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineRequest {

    @Min(1)
    @NotNull
    private Integer idUser;

    @Size(min = 3, max = 15)
    @NotNull
    private String name;

    @Min(0)
    @NotNull
    private int totalTime;

    @Min(1)
    @Max(5)
    @NotNull
    private int difficulty;

    public Routine toNewEntity(User user) {
        return new Routine(user, getName(), getTotalTime(), getDifficulty());
    }
}
