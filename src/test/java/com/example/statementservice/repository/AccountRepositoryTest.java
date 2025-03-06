package com.example.statementservice.repository;

import com.example.statementservice.model.Account;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@TestMethodOrder(OrderAnnotation.class)
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        // Initialize test data before each test method
        account = new Account("000009093817625");
    }

    @Order(1)
    @Test
    void testAdd() {
        accountRepository.save(account);
    }

    @Order(2)
    @Test
    public void testFindByAccountNumber() {
        Account result = accountRepository.findByAccountNumber("000009093817625");
        assertNotNull(result);
    }

    @AfterEach
    void tearDown() {
        // Release test data after each test method
        accountRepository.delete(account);
    }
}
