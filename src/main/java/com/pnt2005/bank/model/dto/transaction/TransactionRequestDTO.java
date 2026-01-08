package com.pnt2005.bank.model.dto.transaction;
import com.pnt2005.bank.enums.TransactionStatus;
import com.pnt2005.bank.model.entity.AccountEntity;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequestDTO {
    @NotNull(message = "fromAccount is required")
    private Long fromAccountId;

    @NotNull(message = "toAccount is required")
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
