package com.pnt2005.bank.model.entity;
import com.pnt2005.bank.enums.UserRole;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<AccountEntity> accountEntityList = new ArrayList<AccountEntity>();

    public void addAccount(AccountEntity accountEntity) {
        accountEntityList.add(accountEntity);
        accountEntity.setUser(this);
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole userRole) {
        this.role = userRole;
    }

    public List<AccountEntity> getAccountEntityList() {
        return accountEntityList;
    }

    public void setAccountEntityList(List<AccountEntity> accountEntityList) {
        this.accountEntityList = accountEntityList;
    }
}
