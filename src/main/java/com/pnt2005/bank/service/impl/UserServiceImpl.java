package com.pnt2005.bank.service.impl;
import com.pnt2005.bank.converter.UserConverter;
import com.pnt2005.bank.enums.UserRole;
import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.repository.UserRepository;
import com.pnt2005.bank.service.UserService;
import com.pnt2005.bank.service.Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Util util;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           Util util,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.util = util;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        List<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            userResponseDTOList.add(userConverter.toUserDTO(userEntity));
        }
        return userResponseDTOList;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        UserEntity userEntity = util.findUserById(id);
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
        UserEntity userEntity = util.findUserById(id);
        userRepository.delete(userEntity);
    }
}
