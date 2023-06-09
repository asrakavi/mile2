package com.example.Mile2.Repository;

import com.example.Mile2.Entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends CrudRepository<Transaction,String> {


   List<Transaction> getTransactionsByPayeeId(String userid);

   List<Transaction> getTransactionsByPayerId(String userid);
}
