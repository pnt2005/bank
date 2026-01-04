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
    private UserEntity user;
    private List<TransactionEntity> outgoingTransactionEntityList = new ArrayList<TransactionEntity>();
    private List<TransactionEntity> incomingTransactionEntityList = new ArrayList<TransactionEntity>();

}
