package com.pnt2005.bank.repository;
import com.pnt2005.bank.annotation.LogExecutionTime;
import com.pnt2005.bank.model.dto.user.UserResponseDTO;
import com.pnt2005.bank.model.entity.UserEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @LogExecutionTime
    @Query(value = """
            select new com.pnt2005.bank.model.dto.user.UserResponseDTO(u.id, u.username)
            from UserEntity u
            where u.username like concat(:username, '%')
            order by u.username
            """
    )
    Slice<UserResponseDTO> findAllByUsername(
            @Param("username") String username,
            Pageable pageable
    );
}
