package com.pnt2005.bank.service;

import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Slice<TransactionResponseDTO> getTransactions(@RequestParam Map<String, String> params);
    TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) throws InterruptedException;
}
