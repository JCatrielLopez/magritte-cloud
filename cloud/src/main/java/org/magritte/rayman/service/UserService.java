package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
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

    public User login(String dni, String password){
        User user = userRepository
                .findByDni(dni)
                .orElseThrow(() -> new UserNotFoundException(dni));
        if (!Objects.equals(user.getPassword(), password)) throw new UserNotValidException();
        return user;
    }
}
