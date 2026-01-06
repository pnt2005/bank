package com.pnt2005.bank.controller;

import com.pnt2005.bank.auth.CustomUserDetails;
import com.pnt2005.bank.model.dto.account.AccountRequestDTO;
import com.pnt2005.bank.model.dto.account.AccountResponseDTO;
import com.pnt2005.bank.model.entity.UserEntity;
import com.pnt2005.bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAccounts(Map<String, String> params) {
        List<AccountResponseDTO> accountResponseDTOList = accountService.getAccounts(params);
        if (accountResponseDTOList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id) {
        AccountResponseDTO accountResponseDTO = accountService.getAccountById(id);
        return ResponseEntity.ok(accountResponseDTO);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(
            @Valid @RequestBody AccountRequestDTO accountRequestDTO,
            Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUser();
        AccountResponseDTO accountResponseDTO = accountService.createAccount(accountRequestDTO, user.getId());
        URI location = URI.create("/accounts/" + accountResponseDTO.getId());
        return ResponseEntity.created(location).body(accountResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
