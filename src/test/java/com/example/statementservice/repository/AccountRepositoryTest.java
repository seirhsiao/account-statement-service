package com.example.statementservice.repository;

import com.example.statementservice.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        // Initialize test data before each test method
        account = new Account("000009093817626");
    }

    @Test
    void testFindByAccountNumber() {
        Account result = accountRepository.save(account);
        assertNotNull(result);

        Account queryResult = accountRepository.findByAccountNumber("000009093817626");
        assertNotNull(queryResult);
    }

    @AfterEach
    void tearDown() {
        // Release test data after each test method
        accountRepository.delete(account);
    }
}
