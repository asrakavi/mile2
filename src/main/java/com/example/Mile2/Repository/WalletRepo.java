package com.example.Mile2.Repository;

import com.example.Mile2.Entity.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepo extends CrudRepository<Wallet,String> {

    boolean existsByMobileNumber(String mobileNumber);

    Wallet findByMobileNumber(String payerMobileNo);




}
