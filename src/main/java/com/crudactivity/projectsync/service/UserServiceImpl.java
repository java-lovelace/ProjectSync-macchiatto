package com.crudactivity.projectsync.service;


import com.crudactivity.projectsync.entity.User;
import com.crudactivity.projectsync.exception.NotFoundException;
import com.crudactivity.projectsync.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + "Not Found"));
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if(getById(id).isEmpty()){
            throw new NotFoundException("User with Id " + id + " Not Found");
        }
        userRepository.deleteById(id);
    }


    @Override
    public User update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User with Id " + user.getId() + " Not Found");
        }
        user.setId(id);
        user.setCreateAt(optionalUser.get().getCreateAt());
        return userRepository.save(user);
    }
}
