package com.pnt2005.bank.repository;
import com.pnt2005.bank.annotation.LogExecutionTime;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @LogExecutionTime
    @Query("""
            select new com.pnt2005.bank.model.dto.user.UserResponseDTO(u.id, u.username)
            from UserEntity u
            where u.username like concat(:username, '%')
            """)
    List<UserResponseDTO> findAllByUsernameStartingWith(@Param("username") String username);
}
