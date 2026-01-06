package com.pnt2005.bank.service;

import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

    void addAccount(UserEntity userEntity, AccountEntity accountEntity);

    UserEntity getUserEntityById(Long id);
}