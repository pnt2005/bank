package com.pnt2005.bank.controller;
import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.model.dto.transaction.TransactionResponseDTO;
import com.pnt2005.bank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Slice<TransactionResponseDTO>> getTransactions(@RequestParam Map<String, String> params) {
        Slice<TransactionResponseDTO> transactionResponseDTOList = transactionService.getTransactions(params);
        return ResponseEntity.ok(transactionResponseDTOList);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) throws InterruptedException {
        TransactionResponseDTO transactionResponseDTO = transactionService.createTransaction(transactionRequestDTO);
        URI location = URI.create("/transactions/" + transactionResponseDTO.getId());
        return ResponseEntity.created(location).body(transactionResponseDTO);
    }
}
