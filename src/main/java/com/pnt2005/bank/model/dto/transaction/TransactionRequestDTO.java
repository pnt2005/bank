package com.pnt2005.bank.model.dto.transaction;
import com.pnt2005.bank.enums.TransactionStatus;
import com.pnt2005.bank.enums.TransactionType;
import com.pnt2005.bank.model.entity.AccountEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequestDTO {
    private Long id;
    private AccountEntity fromAccount;
    private AccountEntity toAccount;
    private BigDecimal amount;
    private LocalDateTime created_at;
    private TransactionType type;
    private TransactionStatus status;
}
