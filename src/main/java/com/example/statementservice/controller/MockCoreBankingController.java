package com.example.statementservice.controller;

import com.example.statementservice.dto.TransactionResponse;
import com.example.statementservice.model.PageInfo;
import com.example.statementservice.model.Transaction;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class MockCoreBankingController {
    @Hidden
    @GetMapping("/api/v2/transactions")
    public TransactionResponse getMockTransactions(
            @RequestParam String accountNumber,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam int pageNo) throws ParseException {
        // Simulate delay
        try {
            int secondsToSleep = 10;
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
        return switch (pageNo) {
            case 1 -> new TransactionResponse(
                    List.of(
                            new Transaction("010000032", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                                    "Fund transfer", "D", 100d, "Friends Name", "AED"),
                            new Transaction("010000033", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                                    "Bill Payment", "D", 500d, "Friends Name", "AED")
                    ),
                    new PageInfo(false, 1));
            case 2 -> new TransactionResponse(
                    List.of(
                            new Transaction("010000034", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                                    "Fund transfer", "D", 100d, "Friends Name", "AED"),
                            new Transaction("010000035", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                                    "Bill Payment", "D", 500d, "Friends Name", "AED")
                    ),
                    new PageInfo(true, 2));
            default -> new TransactionResponse(List.of(), new PageInfo(true, pageNo));
        };
    }
}