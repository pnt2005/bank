package com.pnt2005.bank.converter;

import com.pnt2005.bank.model.dto.account.AccountRequestDTO;
import com.pnt2005.bank.model.dto.account.AccountResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    private final ModelMapper modelMapper;

    public AccountConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountResponseDTO toAccountDTO(AccountEntity accountEntity) {
        AccountResponseDTO accountResponseDTO = modelMapper.map(accountEntity, AccountResponseDTO.class);
        accountResponseDTO.setUsername(accountEntity.getUser().getUsername());
        return accountResponseDTO;
    }

    public AccountEntity toAccountEntity(AccountRequestDTO accountRequestDTO) {
        return modelMapper.map(accountRequestDTO, AccountEntity.class);
    }
}
