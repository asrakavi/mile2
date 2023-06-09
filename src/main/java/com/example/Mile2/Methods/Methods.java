package com.example.Mile2.Methods;

import com.example.Mile2.Entity.Transaction;
import com.example.Mile2.Repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class Methods {
    @Autowired
    public TransactionRepo transactionRepo;

    public Transaction successfulTransaction(String payerId, String payeeId, BigDecimal amount){
        Transaction transaction = new Transaction();
        transaction.setPayeeId(payeeId);
        transaction.setPayerId(payerId);
        transaction.setStatus("Success");
        transaction.setAmount(amount);
        return transaction;
    }

    public Transaction failTransaction(String payerId,String payeeId, BigDecimal amount){
        Transaction transaction = new Transaction();
        transaction.setStatus("failure");
        transaction.setAmount(amount);
        transaction.setPayeeId(payeeId);
        transaction.setPayerId(payerId);
        return transaction;
    }

}

