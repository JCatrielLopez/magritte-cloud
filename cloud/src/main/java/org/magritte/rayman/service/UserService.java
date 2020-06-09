package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.magritte.rayman.exceptions.InvalidInput;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
        if (patient.getUserType() == 'P' && medic.getUserType() == MEDIC) {
            ((Patient) patient).setMedic((Medic) medic);
            userRepository.save(patient);
        }
        else throw new InvalidInput();
//        userRepository.setMedicToPatient(id, medic.getId());
    }

    @Transactional
    public List<PatientResponse> getPatientsFromMedic(Integer id) {
        User medic = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (medic.getUserType() == MEDIC) {
            Medic m = (Medic) medic;
            Set<Patient> lp = m.getPatients();
            List<PatientResponse> salida = new ArrayList<>();
            for (Patient p : lp){
                PatientResponse paraAgregar = new PatientResponse(p);
                salida.add(paraAgregar);
            }
            return salida;
        }
        else throw new InvalidInput();
    }

    public List<PatientResponse> getPatients() {
        List<User> listaUsuarios = userRepository.findAll();
        List<PatientResponse> salida = new ArrayList<>();
        for (User u : listaUsuarios){
            if (u.getUserType() == 'P'){
                PatientResponse pr = new PatientResponse(u);
                salida.add(pr);
            }
        }
        return salida;
    }

    public List<MedicResponse> getMedics() {
        List<User> listaUsuarios = userRepository.findAll();
        List<MedicResponse> salida = new ArrayList<>();
        for (User u : listaUsuarios){
            if (u.getUserType() == MEDIC){
                MedicResponse mr = new MedicResponse(u);
                salida.add(mr);
            }
        }
        return salida;
    }

    public List<UserResponse> allUsers() {
        List<User> listaUsuarios = userRepository.findAll();
        List<UserResponse> salida = new ArrayList<>();
        for (User u : listaUsuarios){
            UserResponse ur = new UserResponse(u);
            salida.add(ur);
        }
        return salida;
    }
}

