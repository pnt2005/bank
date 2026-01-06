package com.pnt2005.bank.service;
import com.pnt2005.bank.exception.ResourceNotFoundException;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class Util {
    private final UserRepository userRepository;

    public Util(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
}
