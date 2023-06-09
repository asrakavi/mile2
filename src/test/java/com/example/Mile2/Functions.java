package com.example.Mile2;

import com.example.Mile2.Entity.Transaction;
import com.example.Mile2.Entity.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Functions {

    @Bean
    public Map<String, Object> getWalletObject(){
        Map<String,Object> map=new HashMap<>();
        map.put("mobileNo","1111111111");
        map.put("amount",10000);
        return map;
    }

    @Bean
    public Map<String, Object> getIdObject(){
        Map<String,Object> map=new HashMap<>();
        map.put("id","1");
        return map;
    }

    @Bean
    public Map<String, Object> getTransactionObject(){
        Map<String,Object> map=new HashMap<>();
        map.put("payee_mobileNumber","1111111111");
        map.put("payer_mobileNumber","1111111112");
        map.put("amount",10000);
        return map;
    }

    @Bean
    public Wallet getWallet(){
        Wallet wallet = new Wallet();
        wallet.setMobileNumber("1111111111");
        wallet.setBankBalance(BigDecimal.ZERO);
        wallet.setId("1");
        return wallet;
    }
    @Bean
    public Wallet getProperWallet1(){
        Wallet wallet = new Wallet();
        wallet.setMobileNumber("1111111111");
        wallet.setBankBalance(BigDecimal.valueOf(10000));
        wallet.setId("1");
        return wallet;
    }
    @Bean
    public Wallet getProperWallet2(){
        Wallet wallet = new Wallet();
        wallet.setMobileNumber("1111111112");
        wallet.setBankBalance(BigDecimal.valueOf(10000));
        wallet.setId("2");
        return wallet;
    }

    @Bean
    public Transaction getTransaction(){
    Transaction transaction=new Transaction(Long.valueOf(1),String.valueOf(2),String.valueOf(1),BigDecimal.valueOf(1000),"success");
    return transaction;
    }

    @Bean
    public List<Transaction> transaction1(){
        List<Transaction> list=new ArrayList<>();
        Transaction transaction=new Transaction(Long.valueOf(1),String.valueOf(2),String.valueOf(1),BigDecimal.valueOf(1000),"success");
        list.add(transaction);
        return list;
    }
    @Bean
    public List<Transaction> transaction2(){
        List<Transaction> list=new ArrayList<>();
        Transaction transaction=new Transaction(Long.valueOf(2),String.valueOf(1),String.valueOf(2),BigDecimal.valueOf(1000),"success");
        list.add(transaction);
        return list;
    }
}
