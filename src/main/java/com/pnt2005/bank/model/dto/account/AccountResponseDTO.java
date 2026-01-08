package com.pnt2005.bank.model.dto.account;
import com.pnt2005.bank.enums.AccountStatus;
import com.pnt2005.bank.enums.AccountType;
import com.pnt2005.bank.model.entity.TransactionEntity;
import com.pnt2005.bank.model.entity.UserEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountResponseDTO {
    private Long id;
    private BigDecimal balance;
    private AccountStatus status;
    private AccountType type;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
