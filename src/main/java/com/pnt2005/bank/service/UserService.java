package com.pnt2005.bank.service;

import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
}
