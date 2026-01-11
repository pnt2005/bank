package com.pnt2005.bank.service.impl;
import com.pnt2005.bank.converter.UserConverter;
import com.pnt2005.bank.enums.UserRole;
import com.pnt2005.bank.exception.ResourceNotFoundException;
import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.repository.UserRepository;
import com.pnt2005.bank.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    public List<UserResponseDTO> getUsers(Map<String, String> params) {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        List<UserEntity> userEntityList;
        if (params.containsKey("username")) {
            userEntityList = userRepository.findAllByUsername(params.get("username").toLowerCase());
        }
        else {
            userEntityList = userRepository.findAll();
        }
        for (UserEntity userEntity : userEntityList) {
            userResponseDTOList.add(userConverter.toUserDTO(userEntity));
        }
        return userResponseDTOList;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        UserEntity userEntity = getUserEntityById(id);
        return userConverter.toUserDTO(userEntity);
    }

    @Override
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
