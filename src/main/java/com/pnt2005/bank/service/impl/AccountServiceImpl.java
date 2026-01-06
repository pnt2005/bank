package com.pnt2005.bank.service.impl;

import com.pnt2005.bank.converter.AccountConverter;
import com.pnt2005.bank.enums.AccountStatus;
import com.pnt2005.bank.exception.ResourceNotFoundException;
import com.pnt2005.bank.model.dto.account.AccountRequestDTO;
import com.pnt2005.bank.model.dto.account.AccountResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.repository.AccountRepository;
import com.pnt2005.bank.service.AccountService;
import com.pnt2005.bank.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final UserService userService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountConverter accountConverter,
                              UserService userService) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.userService = userService;
    }

    @Override
    public List<AccountResponseDTO> getAccounts(Map<String, String> params) {
        List<AccountResponseDTO> accountResponseDTOList = new ArrayList<>();
        List<AccountEntity> accountEntityList;
        if (params.containsKey("username")) {
            accountEntityList = accountRepository.findByUserUsername(params.get("username").toLowerCase());
        }
        else {
            accountEntityList = accountRepository.findAll();
        }
        for (AccountEntity accountEntity : accountEntityList) {
            accountResponseDTOList.add(accountConverter.toAccountDTO(accountEntity));
        }
        return accountResponseDTOList;
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        return null;
    }

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO, Long id) {
        AccountEntity accountEntity = accountConverter.toAccountEntity(accountRequestDTO);
        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountEntity.setBalance(BigDecimal.ZERO);
        UserEntity userEntity = userService.getUserEntityById(id);
        userService.addAccount(userEntity, accountEntity);
        return accountConverter.toAccountDTO(accountRepository.save(accountEntity));
    }

    @Override
    public void deleteAccount(Long id) {
        AccountEntity accountEntity = getAccountEntityById(id);
        accountRepository.delete(accountEntity);
    }

    @Override
    public AccountEntity getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));
    }
}
