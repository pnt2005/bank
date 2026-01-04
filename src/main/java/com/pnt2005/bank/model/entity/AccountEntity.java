package com.pnt2005.bank.model.entity;
import com.pnt2005.bank.enums.AccountStatus;
import com.pnt2005.bank.enums.AccountType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "fromAccount")
    private List<TransactionEntity> outgoingTransactionEntityList = new ArrayList<TransactionEntity>();

    @OneToMany(mappedBy = "toAccount")
    private List<TransactionEntity> incomingTransactionEntityList = new ArrayList<TransactionEntity>();

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TransactionEntity> getOutgoingTransactionEntityList() {
        return outgoingTransactionEntityList;
    }

    public void setOutgoingTransactionEntityList(List<TransactionEntity> outgoingTransactionEntityList) {
        this.outgoingTransactionEntityList = outgoingTransactionEntityList;
    }

    public List<TransactionEntity> getIncomingTransactionEntityList() {
        return incomingTransactionEntityList;
    }

    public void setIncomingTransactionEntityList(List<TransactionEntity> incomingTransactionEntityList) {
        this.incomingTransactionEntityList = incomingTransactionEntityList;
    }
}
