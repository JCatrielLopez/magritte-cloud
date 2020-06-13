package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.data.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Optional<Session> getSessionByRoutineAndName(Routine routine, String name) {
        return sessionRepository.getSessionByRoutineAndName(routine, name);
    }
}
