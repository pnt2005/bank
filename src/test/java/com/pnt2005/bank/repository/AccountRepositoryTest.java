package com.pnt2005.bank.repository;

import com.pnt2005.bank.model.dto.transaction.TransactionRequestDTO;
import com.pnt2005.bank.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.concurrent.*;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AccountRepositoryTest {
    @Autowired
    TransactionServiceImpl transactionService;

    @Test
    void testLockAccount() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        Future<?> thread1 = executorService.submit(() -> {
            latch.await(); //wait for start together
            TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
            transactionRequestDTO.setAmount(new BigDecimal(100));
            transactionRequestDTO.setFromAccountId(1L);
            transactionRequestDTO.setToAccountId(2L);
            transactionService.createTransaction(transactionRequestDTO);
            return null;
        });

        Future<?> thread2 = executorService.submit(() -> {
            latch.await(); //wait for start together
            TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
            transactionRequestDTO.setAmount(new BigDecimal(100));
            transactionRequestDTO.setFromAccountId(1L);
            transactionRequestDTO.setToAccountId(2L);
            transactionService.createTransaction(transactionRequestDTO);
            return null;
        });

        latch.countDown(); //start
        long start = System.currentTimeMillis();
        thread1.get();
        thread2.get();
        long time = System.currentTimeMillis() - start;
        assertTrue(time >= 4000); //each thread sleep 2s
    }
}
