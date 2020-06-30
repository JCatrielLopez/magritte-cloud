package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserResponse login(@NotNull String nickname, @NotNull String password) {
        User user = userRepository.findByNickname(nickname).orElseThrow(NoSuchElementException::new);
        if (!Objects.equals(user.getPassword(), password)) return null;
        return user.getUserType() == MEDIC ? new MedicResponse(user) : new PatientResponse(user);
    }

    public List<PatientResponse> getPatientsFromMedic(@NotNull Integer id) {
        Medic medic = (Medic) userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return medic.getPatient().stream()
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

    public List<MedicResponse> getMedicsBySpecialization(@NotNull String specialization){
        List<MedicResponse> medicos = this.getMedics();
        List<MedicResponse> salida = new ArrayList<>();
        for (MedicResponse m : medicos){
            if (m.getSpecialization().equals(specialization))
                salida.add(m);
        }
        return salida;
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

    public PatientResponse setMedicToPatient(Integer idPatient, Integer idMedic){
        Patient patient = (Patient) this.getUserById(idPatient);
        Medic medic = (Medic) this.getUserById(idMedic);
        patient.addMedic(medic);
        medic.addPatient(patient);
        this.save(medic);
        return new PatientResponse(patient);
    }

    public List<MedicResponse> getMedicsFromPatient(Integer id) {
        Patient patient = (Patient) userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return patient.getMedic().stream()
                .map(MedicResponse::new)
                .collect(Collectors.toList());
    }

    public Medic changeAvailability(Integer id) {
        Medic medic = (Medic) userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        medic.setAvailability(!medic.isAvailability());
        userRepository.save(medic);
        return medic;
    }

    public List<MedicResponse> getMedicsByCity(String city) {
        List<MedicResponse> medicos = this.getMedics();
        List<MedicResponse> salida = new ArrayList<>();
        for (MedicResponse m : medicos){
            if (m.getCity().equals(city))
                salida.add(m);
        }
        return salida;
    }
}

