package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.magritte.rayman.rest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        return new UserResponse(user);
    }

    public void signUpMedic(Integer id) {
        // TODO
        Optional<User> patient = userRepository.findById(id);
//        User medic = userRepository.findByDni(dni).orElseThrow(() -> new UserNotFoundException("Medic not found!"));
//        patient.setMedic(medic);
//        userRepository.save(patient);
        User user = null;
        if (patient.isPresent()) {
            user = patient.get();
        }
        assert user != null;
        System.out.println(user.toString());
        //        userRepository.setMedicToPatient(id, medic.getId());
    }
}

