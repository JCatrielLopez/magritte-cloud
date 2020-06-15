package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.magritte.rayman.data.entity.Medic.MEDIC;
import static org.magritte.rayman.data.entity.Patient.PATIENT;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(@NotNull Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void save(@NotNull User user) {
        userRepository.save(user);
    }

    public void delete(@NotNull User user) {
        userRepository.delete(user);
    }

    public UserResponse login(@NotNull String dni, @NotNull String password) {
        User user = userRepository.findByDni(dni).orElseThrow(NoSuchElementException::new);
        if (!Objects.equals(user.getPassword(), password)) return null;
        return user.getUserType() == MEDIC ? new MedicResponse(user) : new PatientResponse(user);
    }

    @Transactional
    public List<PatientResponse> getPatientsFromMedic(@NotNull Integer id) {
        Medic medic = (Medic) userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return medic.getPatients().stream()
                .map(PatientResponse::new)
                .collect(Collectors.toList());
    }

    public List<PatientResponse> getPatients() {
        return userRepository.findByUserType(PATIENT).stream()
                .map(PatientResponse::new)
                .collect(Collectors.toList());
    }

    public List<MedicResponse> getMedics() {
        return userRepository.findByUserType(MEDIC).stream()
                .map(MedicResponse::new)
                .collect(Collectors.toList());
    }

    public List<UserResponse> allUsers() {
        return new ArrayList<>() {{
            addAll(getPatients());
            addAll(getMedics());
        }};
    }

    public User getUserByDni(@NotNull String dni) {
        return userRepository
                .findByDni(dni)
                .orElseThrow(NoSuchElementException::new);
    }
}

