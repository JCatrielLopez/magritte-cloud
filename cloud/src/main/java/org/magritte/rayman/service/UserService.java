package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.magritte.rayman.exceptions.UserNotValidException;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.magritte.rayman.data.entity.Medic.MEDIC;
import static org.magritte.rayman.data.entity.Patient.PATIENT;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public UserResponse login(String dni, String password) {
        User user = userRepository.findByDni(dni).orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(user.getPassword(), password)) throw new UserNotValidException();
        return user.getUserType() == MEDIC ? new MedicResponse(user) : new PatientResponse(user);
    }

    public void setMedicToPatient(Integer idPatient, Integer idMedic) {
        Patient patient = (Patient) userRepository.findById(idPatient).orElseThrow(UserNotFoundException::new);
        Medic medic = (Medic) userRepository.findById(idMedic).orElseThrow(UserNotFoundException::new);
        patient.setMedic(medic);
        userRepository.save(patient);
    }

    @Transactional
    public List<PatientResponse> getPatientsFromMedic(Integer id) {
        Medic medic = (Medic) userRepository.findById(id).orElseThrow(UserNotFoundException::new);
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

    public User getUserByDni(String dni) {
        return userRepository
                .findByDni(dni)
                .orElseThrow(UserNotFoundException::new);
    }
}

