package com.example.Mile2.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 *
 */
@Entity
@Table(name="Transaction")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long TransactionId;
    private String payerId;
    private String payeeId;
    private BigDecimal amount;
    private String status;

    /**
     * @param transactionId
     * @param payerId
     * @param payeeId
     * @param amount
     * @param status
     */
    public Transaction(long transactionId, String payerId, String payeeId, BigDecimal amount, String status) {

        TransactionId = transactionId;
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.amount = amount;
        this.status = status;
    }

    public Transaction() {
    }

    public long getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(long transactionId) {
        TransactionId = transactionId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "TransactionId='" + TransactionId + '\'' +
                ", payerId='" + payerId + '\'' +
                ", payeeId='" + payeeId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
