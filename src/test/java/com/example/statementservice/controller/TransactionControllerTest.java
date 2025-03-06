package com.example.statementservice.controller;

import com.example.statementservice.model.Account;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.service.TransactionService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void getTransactions_shouldReturnAcceptedWithResultPath() throws Exception {
        String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
        List<Transaction> transactions = List.of(
                new Transaction("010000032", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                        "Fund transfer", "D", 100d, "Friends Name", "AED"),
                new Transaction("010000033", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                        "Bill Payment", "D", 500d, "Friends Name", "AED")
        );
        Page<Transaction> page = new PageImpl<>(transactions);
        when(transactionService.findByAccountAndBetweenValueDate(any(Account.class), anyString(), anyString(), anyInt(), anyInt())).thenReturn(page);
        mockMvc.perform(get("/api/v1/transactions")
                        .param("accountNumber", "1000000001")
                        .param("fromDate", "2024-11-01")
                        .param("toDate", "2024-11-30")
                        .param("pageNo", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").exists());
    }

    @Test
    void getTransactions_shouldReturnProcessingWhenStatusProcessing() throws Exception {
        when(transactionService.findByAccountAndBetweenValueDate(any(Account.class), anyString(), anyString(), anyInt(), anyInt())).thenReturn(Page.empty());
        mockMvc.perform(get("/api/v1/transactions")
                        .param("accountNumber", "1000000001")
                        .param("fromDate", "2024-11-01")
                        .param("toDate", "2024-11-30")
                        .param("pageNo", "2")
                )
                .andExpect(status().isOk());
    }
}