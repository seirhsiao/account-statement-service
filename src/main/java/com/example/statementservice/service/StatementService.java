package com.example.statementservice.service;

import com.example.statementservice.config.TaskCache;
import com.example.statementservice.model.TaskResult;
import com.example.statementservice.model.TaskStatus;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.util.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementService {
    private final Logger logger = LoggerFactory.getLogger(StatementService.class);

    @Autowired
    private RestClient restClient;

    @Autowired
    private TaskCache taskCache;

    public void generateStatements(String taskId, String fromDate, String toDate, String accountNumber) {
        try {
            List<Transaction> transactions = restClient.getAllTransactions(accountNumber, fromDate, toDate);
            String pdf = restClient.generatePdf(transactions);
            taskCache.put(taskId, new TaskResult(TaskStatus.COMPLETED, pdf));
            logger.info("Generated PDF for task : {}", taskId);
        } catch (Exception e) {
            taskCache.put(taskId, new TaskResult(TaskStatus.FAILED, null));
            logger.warn("Task failed", e);
        }
    }
}