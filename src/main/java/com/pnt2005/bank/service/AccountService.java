package com.pnt2005.bank.service;

import com.pnt2005.bank.model.dto.account.AccountRequestDTO;
import com.pnt2005.bank.model.dto.account.AccountResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface AccountService {
    List<AccountResponseDTO> getAccounts(Map<String, String> params);
    AccountResponseDTO getAccountById(Long id);
    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO, Long id);
    void deleteAccount(Long id);
    AccountEntity getAccountEntityById(Long id);
}
