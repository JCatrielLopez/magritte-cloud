package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.magritte.rayman.data.entity.Medic.MEDIC;

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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public UserResponse login(String dni, String password) {
        User user = userRepository
                .findByDni(dni)
                .orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(user.getPassword(), password)) return null;
        return user.getUserType() == MEDIC ? new MedicResponse((Medic) user) : new PatientResponse((Patient) user);
    }

    public void setMedicToPatient(Integer idPatient, Integer idMedic) {
        User patient = userRepository.findById(idPatient).orElseThrow(UserNotFoundException::new);
        User medic = userRepository.findById(idMedic).orElseThrow(UserNotFoundException::new);
        ((Patient) patient).setMedic((Medic) medic);
        userRepository.save(patient);
//        userRepository.setMedicToPatient(id, medic.getId());
    }
}

