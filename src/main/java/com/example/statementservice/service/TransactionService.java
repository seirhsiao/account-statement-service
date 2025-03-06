package com.example.statementservice.service;

import com.example.statementservice.model.Account;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.repository.TransactionRepository;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    @Autowired
    TransactionRepository transactionRepository;

    public Page<Transaction> findByAccountAndBetweenValueDate(Account account, String from, String to, int pageNo, int pageSize) {
        Specification<Transaction> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String datePattern = "dd-MM-yyyy";
            Date beginTime = new Date();
            Date endTime = new Date();
            try {
                beginTime = DateUtils.parseDate(from, datePattern);
                endTime = DateUtils.parseDate(to, datePattern);
            } catch (ParseException e) {
                logger.info("failed converted fromDate : {} or toDate : {} with format : {}", beginTime, endTime, datePattern);
                throw new RuntimeException(e);
            }
            predicates.add(criteriaBuilder.equal(root.get("account"), account));
            predicates.add(criteriaBuilder.between(root.get("valueDate"), beginTime, endTime));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("trxReferenceNo").ascending());
        return transactionRepository.findAll(pageable);
    }
}
