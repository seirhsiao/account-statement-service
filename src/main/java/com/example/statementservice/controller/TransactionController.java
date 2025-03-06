package com.example.statementservice.controller;

import com.example.statementservice.dto.TransactionResponse;
import com.example.statementservice.model.Account;
import com.example.statementservice.model.PageInfo;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "transactions", description = "the transactions API")
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    public static final int PAGE_SIE = 2;

    @GetMapping("/api/v1/transactions")
    public TransactionResponse getTransactions(
            @RequestParam String accountNumber,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam int pageNo) {
        Account account = new Account(accountNumber);
        // db page number start with 0
        int pageNumber = pageNo - 1;
        Page<Transaction> transactions = transactionService.findByAccountAndBetweenValueDate(account, fromDate, toDate, pageNumber, PAGE_SIE);
        int totalPages = transactions.getTotalPages();
        PageInfo pageInfo = null;
        if (pageNo == totalPages) {
            pageInfo = new PageInfo(true, pageNo);
        } else {
            pageInfo = new PageInfo(false, pageNo);
        }
        return new TransactionResponse(transactions.getContent(), pageInfo);
    }
}