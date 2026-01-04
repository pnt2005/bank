package com.pnt2005.bank.model.entity;
import com.pnt2005.bank.enums.TransactionStatus;
import com.pnt2005.bank.enums.TransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account")
    private AccountEntity fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private AccountEntity toAccount;

    private BigDecimal amount;
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountEntity getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountEntity fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountEntity getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountEntity toAccount) {
        this.toAccount = toAccount;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
