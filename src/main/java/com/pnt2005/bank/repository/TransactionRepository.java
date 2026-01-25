package com.pnt2005.bank.repository;
import com.pnt2005.bank.annotation.LogExecutionTime;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import com.pnt2005.bank.model.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @LogExecutionTime
    @Query(value = """
            select new com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO(
                t.id, t.fromAccountEntity.id, t.toAccountEntity.id, t.amount, t.created_at, t.transactionStatus
            )
            from TransactionEntity t
            join t.fromAccountEntity a
            join a.user u
            where u.username = :username
            and (:from is null or t.created_at >= :from)
            and (:to is null or t.created_at <= :to)
            """
    )
    Slice<TransactionResponseDTO> findAllByUserName(
            @Param("username") String username,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable
    );
}
