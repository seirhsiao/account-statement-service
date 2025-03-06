package com.example.statementservice.repository;

import com.example.statementservice.model.Account;
import com.example.statementservice.model.Transaction;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    private Transaction transaction;
    private Account account;

    String datePattern = "yyyy-MM-dd'T'HH:mm:ss";

    @BeforeEach
    void setUp() throws ParseException {
        // Initialize test data before each test method
        transaction = new Transaction("010000032", DateUtils.parseDate("2024-11-12T00:00:00", datePattern), "Fund transfer", "D", 100d, "Friends Name", "AED");
        account = new Account("000009093817625");
        transaction.setAccount(account);
    }

    @Test
    public void testFindById() {
        Transaction saveResult = transactionRepository.save(transaction);
        assertNotNull(saveResult);
        Optional<Transaction> definitions = transactionRepository.findById("010000032");
        if (definitions.isPresent()) {
            Transaction result = definitions.get();
            assertEquals("AED", result.getTranCurrency());
        }
    }

    @AfterEach
    void tearDown() {
        // Release test data after each test method
        transactionRepository.delete(transaction);
    }
}
