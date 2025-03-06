package com.example.statementservice.repository;

import com.example.statementservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, String> {
//    List<Transaction> findAllByAccountNumberAndValueDateBetween(Account account, Date from, Date to);
}
