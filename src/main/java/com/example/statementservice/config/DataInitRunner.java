package com.example.statementservice.config;

import com.example.statementservice.model.Account;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.repository.TransactionRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;


@Component
public class DataInitRunner implements ApplicationRunner {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(ApplicationArguments args) {
        Account account = new Account("000009093817625");
        String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
        try {
            Transaction transaction1 = new Transaction("010000032", DateUtils.parseDate("2024-11-12T00:00:00", datePattern), "Fund transfer", "D", 100d, "Friends Name", "AED");
            Transaction transaction2 = new Transaction("010000033", DateUtils.parseDate("2024-11-12T00:00:00", datePattern), "Bill Payment",
                    "D", 500d, "Friends Name", "AED");
            Transaction transaction3 = new Transaction("010000034", DateUtils.parseDate("2024-11-12T00:00:00", datePattern), "Fund transfer",
                    "D", 100d, "Friends Name", "AED");
            Transaction transaction4 = new Transaction("010000035", DateUtils.parseDate("2024-11-12T00:00:00", datePattern), "Bill Payment",
                    "D", 500d, "Friends Name", "AED");
            transaction1.setAccount(account);
            transaction2.setAccount(account);
            transaction3.setAccount(account);
            transaction4.setAccount(account);

            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);
            transactionRepository.save(transaction3);
            transactionRepository.save(transaction4);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}