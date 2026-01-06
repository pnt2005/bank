package com.pnt2005.bank.converter;

import com.pnt2005.bank.model.dto.user.UserRequestDTO;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO toUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    public UserEntity toUserEntity(UserRequestDTO userRequestDTO) {
        return modelMapper.map(userRequestDTO, UserEntity.class);
    }
}
