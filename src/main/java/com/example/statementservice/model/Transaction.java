package com.example.statementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Transaction implements Serializable {

    @Id
    @Column(name = "TRX_REFERENCE_NO")
    private String trxReferenceNo;
    @Column(name = "VALUE_DATE")
    private Date valueDate;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TRX_TYPE")
    private String trxType;
    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "BENEFICIARY_DETAILS")
    private String beneficiaryDetails;
    @Column(name = "TRAN_CURRENCY")
    private String tranCurrency;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "account_number")
    @JsonIgnore
    private Account account;

    public Transaction() {
    }

    public Transaction(String trxReferenceNo, Date valueDate, String description, String trxType, Double amount, String beneficiaryDetails, String tranCurrency) {
        this.trxReferenceNo = trxReferenceNo;
        this.valueDate = valueDate;
        this.description = description;
        this.trxType = trxType;
        this.amount = amount;
        this.beneficiaryDetails = beneficiaryDetails;
        this.tranCurrency = tranCurrency;
    }

    public String getTrxReferenceNo() {
        return trxReferenceNo;
    }

    public void setTrxReferenceNo(String trxReferenceNo) {
        this.trxReferenceNo = trxReferenceNo;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBeneficiaryDetails() {
        return beneficiaryDetails;
    }

    public void setBeneficiaryDetails(String beneficiaryDetails) {
        this.beneficiaryDetails = beneficiaryDetails;
    }

    public String getTranCurrency() {
        return tranCurrency;
    }

    public void setTranCurrency(String tranCurrency) {
        this.tranCurrency = tranCurrency;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
