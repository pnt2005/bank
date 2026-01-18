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

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @LogExecutionTime
    @Query(value = """
            select main.id, main.username, main.email
            from (
                select u.id
                from users u
                where u.username like concat(:username, '%')
                order by u.username
                limit :limit offset :offset
            )
            as temp
            inner join users as main
            on temp.id = main.id
            """,
            nativeQuery = true
    )
    List<Object[]> findAllByUsername(
            @Param("username") String username,
            @Param("limit") int limit,
            @Param("offset") long offset
    );
}
