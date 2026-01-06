package com.pnt2005.bank.model.dto.user;

import com.pnt2005.bank.enums.UserRole;
import com.pnt2005.bank.model.dto.account.AccountResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
