package com.example.statementservice.dto;

import com.example.statementservice.model.Transaction;

import java.util.List;

public record GeneratePdfRequest(
        String templateId,
        List<Transaction> data
) {}