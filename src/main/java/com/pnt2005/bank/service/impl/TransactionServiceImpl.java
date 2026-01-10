package com.pnt2005.bank.service.impl;

import com.pnt2005.bank.converter.TransactionConverter;
import com.pnt2005.bank.enums.AccountStatus;
import com.pnt2005.bank.enums.TransactionStatus;
import com.pnt2005.bank.exception.TransactionException;
import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.TransactionEntity;
import com.pnt2005.bank.repository.AccountRepository;
import com.pnt2005.bank.repository.TransactionRepository;
import com.pnt2005.bank.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionConverter transactionConverter,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionResponseDTO> getTransactions() {
        List<TransactionResponseDTO> transactionResponseDTOList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = transactionRepository.findAll();
        for (TransactionEntity transactionEntity : transactionEntityList) {
            transactionResponseDTOList.add(transactionConverter.toTransactionDTO(transactionEntity));
        }
        return transactionResponseDTOList;
    }

    @Override
    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        //get fromAccount and toAccount
        AccountEntity fromAccountEntity = accountRepository.findByIdForUpdate(transactionRequestDTO.getFromAccountId());
        AccountEntity toAccountEntity = accountRepository.findByIdForUpdate(transactionRequestDTO.getToAccountId());
        //validate
        if (!fromAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new TransactionException("from account is not active");
        }
        if (!toAccountEntity.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new TransactionException("to account is not active");
        }
        if (fromAccountEntity.getBalance().compareTo(transactionRequestDTO.getAmount()) < 0) {
            throw new TransactionException("amount > balance");
        }
        //update balance
        fromAccountEntity.setBalance(fromAccountEntity.getBalance().subtract(transactionRequestDTO.getAmount()));
        toAccountEntity.setBalance(toAccountEntity.getBalance().add(transactionRequestDTO.getAmount()));
        //save transactionEntity
        TransactionEntity transactionEntity = transactionConverter.toTransactionEntity(transactionRequestDTO);
        TransactionResponseDTO transactionResponseDTO = transactionConverter.toTransactionDTO(transactionRepository.save(transactionEntity));
        //update accountEntity
        fromAccountEntity.addOutgoingTransactionEntityList(transactionEntity);
        accountRepository.save(fromAccountEntity);
        toAccountEntity.addIncomingTransactionEntityList(transactionEntity);
        accountRepository.save(toAccountEntity);
        return transactionResponseDTO;
    }
}
