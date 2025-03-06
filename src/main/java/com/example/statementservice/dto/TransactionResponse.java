package com.example.statementservice.dto;

import com.example.statementservice.model.PageInfo;
import com.example.statementservice.model.Transaction;

import java.util.List;

public record TransactionResponse(
        List<Transaction> transactions,
        PageInfo page
) {}