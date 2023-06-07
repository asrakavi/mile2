package com.example.Mile2.Controller;

import com.example.Mile2.Entity.Transaction;
import com.example.Mile2.Entity.Wallet;
import com.example.Mile2.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class WalletController {
@Autowired
private WalletService walletService;


    public WalletController() {
    }
    @GetMapping("/Home")
    public String Home(){
    return "This is the home page";
}


    /**
     * @param wallet
     * @return
     */


    @PostMapping("/Wallet")
    public Wallet walletCreation(@RequestBody Wallet wallet){
        return walletService.createWallet(wallet);
    }

    @PutMapping("/Add")
    public String addMoney(@RequestBody Map<String,Object> data){
        return walletService.addInWallet(data);
    }

    @PostMapping("/Transaction")
    public ResponseEntity<?> Transaction(@RequestBody Map<String,Object> TransactionData){
        return walletService.moneyTransfer(TransactionData);
    }

    @GetMapping("/Transactions")
    public List<Transaction> listOfTransactionByUser(@RequestBody Map<String,Object> data){
        return walletService.getTransactionsByUser(data);
    }

    @GetMapping("/Status/{id}")
    public String statusOfTransaction(@PathVariable String id){
        return walletService.transactionStatus(id);
    }

}
