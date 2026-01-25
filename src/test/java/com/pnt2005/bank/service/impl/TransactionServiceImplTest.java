package com.pnt2005.bank.service.impl;
import com.pnt2005.bank.converter.TransactionConverter;
import com.pnt2005.bank.enums.AccountStatus;
import com.pnt2005.bank.exception.TransactionException;
import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.TransactionEntity;
import com.pnt2005.bank.repository.AccountRepository;
import com.pnt2005.bank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    TransactionConverter transactionConverter;
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;

    AccountEntity fromAccount = new AccountEntity();
    AccountEntity toAccount = new AccountEntity();
    TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
    TransactionEntity transactionEntity = new TransactionEntity();
    TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

    @BeforeEach
    void setUp() {
        //account
        fromAccount.setId(1L);
        toAccount.setId(1L);
        fromAccount.setStatus(AccountStatus.ACTIVE);
        toAccount.setStatus(AccountStatus.ACTIVE);
        //transaction request
        transactionRequestDTO.setFromAccountId(1L);
        transactionRequestDTO.setToAccountId(2L);
        //when
        when(accountRepository.findByIdForUpdate(1L)).thenReturn(fromAccount);
        when(accountRepository.findByIdForUpdate(2L)).thenReturn(toAccount);
    }

    @Test
    void createTransaction_success() throws InterruptedException {
        //given
        fromAccount.setBalance(new BigDecimal(100));
        toAccount.setBalance(new BigDecimal(200));
        transactionRequestDTO.setAmount(new BigDecimal(50));
        when(transactionConverter.toTransactionEntity(transactionRequestDTO))
                .thenReturn(transactionEntity);
        when(transactionConverter.toTransactionDTO(transactionEntity))
                .thenReturn(transactionResponseDTO);
        when(transactionRepository.save(transactionEntity))
                .thenReturn(transactionEntity);
        //when
        TransactionResponseDTO transactionResponseDTO = transactionServiceImpl.createTransaction(transactionRequestDTO);
        //then
        assertEquals(new BigDecimal(50), fromAccount.getBalance());
        assertEquals(new BigDecimal(250), toAccount.getBalance());
        assertNotNull(transactionResponseDTO);
    }

    @Test
    void createTransaction_shouldThrowException_whenBalanceNotEnough() {
        //given
       fromAccount.setBalance(new BigDecimal(100));
       transactionRequestDTO.setAmount(new BigDecimal(500));
        //when & then
        assertThrows(TransactionException.class,
                () -> transactionServiceImpl.createTransaction(transactionRequestDTO)
        );
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void createTransaction_shouldThrowException_whenFromAccountNotActive() {
        //given
        fromAccount.setStatus(AccountStatus.BLOCKED);
        //when & then
        assertThrows(TransactionException.class,
                () -> transactionServiceImpl.createTransaction(transactionRequestDTO)
        );
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void createTransaction_shouldThrowException_whenToAccountNotActive() {
        //given
        toAccount.setStatus(AccountStatus.BLOCKED);
        //when & then
        assertThrows(TransactionException.class,
                () -> transactionServiceImpl.createTransaction(transactionRequestDTO)
        );
        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }
}