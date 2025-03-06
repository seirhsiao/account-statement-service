package com.example.statementservice.repository;

import com.example.statementservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TransactionRepository extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {
    Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageable);
//    List<Transaction> findAllByAccountNumberAndValueDateBetween(Account account, Date from, Date to);
}
