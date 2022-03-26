package com.baonguyen.UserManager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        Optional<User> studentOptional = userRepository.findUserByEmail(user.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Email is already used!");
        }
        userRepository.save(user);
    }

    public User get(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new IllegalStateException("User with ID " + id + " does not exists");
        }
    }

    public void delete(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            userRepository.deleteById(id);
        }
        else {
            throw new IllegalStateException("User with ID " + id + " does not exists");
        }
    }
}


