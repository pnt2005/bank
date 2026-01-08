package com.pnt2005.bank.converter;
import com.pnt2005.bank.enums.TransactionStatus;
import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import com.pnt2005.bank.model.entity.AccountEntity;
import com.pnt2005.bank.model.entity.TransactionEntity;
import com.pnt2005.bank.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class TransactionConverter {
    private final ModelMapper modelMapper;
    private final AccountService accountService;

    public TransactionConverter(ModelMapper modelMapper,
                                AccountService accountService) {
        this.modelMapper = modelMapper;
        this.accountService = accountService;
    }

    public TransactionResponseDTO toTransactionDTO(TransactionEntity transactionEntity) {
        TransactionResponseDTO transactionResponseDTO = modelMapper.map(transactionEntity, TransactionResponseDTO.class);
        transactionResponseDTO.setFromAccountId(transactionEntity.getFromAccountEntity().getId());
        transactionResponseDTO.setToAccountId(transactionEntity.getToAccountEntity().getId());
        return transactionResponseDTO;
    }

    public TransactionEntity toTransactionEntity(TransactionRequestDTO transactionRequestDTO) {
        TransactionEntity transactionEntity = modelMapper.map(transactionRequestDTO, TransactionEntity.class);
       transactionEntity.setFromAccountEntity(
                accountService.getAccountEntityById(transactionRequestDTO.getFromAccountId())
        );
        transactionEntity.setToAccountEntity(
                accountService.getAccountEntityById(transactionRequestDTO.getToAccountId())
        );
        transactionEntity.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionEntity.setCreated_at(LocalDateTime.now());
        return transactionEntity;
    }
}
