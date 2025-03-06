package com.example.statementservice.service;

import com.example.statementservice.model.Account;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testFindByAccountAndBetweenValueDate() {
        when(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(Page.empty());
        Page<Transaction> pages = transactionService.findByAccountAndBetweenValueDate(new Account("aaa"), "01-08-2024", "31-08-2024", 1, 2);
        assertNull(pages);
    }
}
