package com.pnt2005.bank.model.entity;
import com.pnt2005.bank.enums.TransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account")
    private AccountEntity fromAccountEntity;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private AccountEntity toAccountEntity;

    private BigDecimal amount;
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountEntity getFromAccountEntity() {
        return fromAccountEntity;
    }

    public void setFromAccountEntity(AccountEntity fromAccountEntity) {
        this.fromAccountEntity = fromAccountEntity;
    }

    public AccountEntity getToAccountEntity() {
        return toAccountEntity;
    }

    public void setToAccountEntity(AccountEntity toAccountEntity) {
        this.toAccountEntity = toAccountEntity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
