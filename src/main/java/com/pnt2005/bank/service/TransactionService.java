package com.pnt2005.bank.service;

import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> getTransactions();
    TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO);
}
