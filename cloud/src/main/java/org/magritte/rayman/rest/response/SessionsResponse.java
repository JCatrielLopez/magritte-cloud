package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SessionsResponse {

    private Set<SessionResponse> sessions;

    public SessionsResponse(Set<SessionResponse> sessions) {
        this.sessions = sessions;
        //noinspection ResultOfMethodCallIgnored
        this.sessions.size();
    }
}
