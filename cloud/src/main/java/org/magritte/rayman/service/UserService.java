package org.magritte.rayman.service;

import org.hibernate.Session;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));
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

    public Character login(String dni, String password){
        User user = userRepository.findByDni(dni).orElseThrow(() -> new UserNotFoundException("User with dni " + dni + " was no found."));
        if(user.getPassword().equals(password)){
            return user.getUserType();
        }
        else return null;
    }

    public boolean registerMedic(Integer id, String dni) {
        Optional<User> patient = userRepository.findById(id);
//        User medic = userRepository.findByDni(dni).orElseThrow(() -> new UserNotFoundException("Medic not found!"));
//        patient.setMedic(medic);
//        userRepository.save(patient);
        System.out.println(patient.toString());
        //        userRepository.setMedicToPatient(id, medic.getId());
        return true;
    }
}

