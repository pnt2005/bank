package com.pnt2005.bank.service.impl;
import com.pnt2005.bank.annotation.LogExecutionTime;
import com.pnt2005.bank.converter.UserConverter;
import com.pnt2005.bank.enums.UserRole;
import com.pnt2005.bank.exception.ResourceNotFoundException;
import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.repository.UserRepository;
import com.pnt2005.bank.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @LogExecutionTime
    public Slice<UserResponseDTO> getUsers(Map<String, String> params) {
        Pageable pageable = PageRequest.of(Integer.parseInt(params.get("pageNumber")), 20);
        List<Object[]> rows = userRepository.findAllByUsername(params.get("username"), pageable.getPageSize(), pageable.getOffset());
        List<UserResponseDTO> content = rows.stream()
                .map(r -> new UserResponseDTO(
                        (Long)r[0],
                        (String)r[1],
                        (String)r[2]
                ))
                .toList();
        boolean hasNext = content.size() == pageable.getPageSize();
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        UserEntity userEntity = getUserEntityById(id);
        return userConverter.toUserDTO(userEntity);
    }

    @Override
    @LogExecutionTime
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userConverter.toUserEntity(userRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userEntity.setRole(UserRole.ROLE_USER);
        return userConverter.toUserDTO(userRepository.save(userEntity));
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = getUserEntityById(id);
        userRepository.delete(userEntity);
    }

    @Override
    public void addAccount(UserEntity userEntity, AccountEntity accountEntity) {
        userEntity.addAccount(accountEntity);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
}
