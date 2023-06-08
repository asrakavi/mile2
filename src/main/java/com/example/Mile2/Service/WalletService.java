package com.example.Mile2.Service;

import com.example.Mile2.Entity.Transaction;
import com.example.Mile2.Entity.Wallet;
import com.example.Mile2.MessageRequest;
import com.example.Mile2.Methods.Methods;
import com.example.Mile2.Repository.TransactionRepo;
import com.example.Mile2.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private TransactionRepo transactionRepo;
    public Methods methods=new Methods();

    private KafkaTemplate<String,String> kafkaTemplate;

    public WalletService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * @param wallet
     * @return
     */

    public Wallet createWallet(Wallet wallet) {
        String regexp = "\\d{10}";
        if(!wallet.getMobileNumber().matches(regexp)){
            throw new IllegalArgumentException("Invalid Mobile Number");
        }
        if(walletRepo.existsByMobileNumber(wallet.getMobileNumber())) {
            throw new IllegalArgumentException("Wallet Already Exists");
        }
        kafkaTemplate.send("wallet", "Wallet created: "+ String.valueOf(wallet));
        return walletRepo.save(wallet);
    }
//    @PostMapping("api/v1/messages")
//    public void publish(@RequestBody MessageRequest request){
//        kafkaTemplate.send("wallet", request.message());
//    }

    public BigDecimal addInWallet(Map<String, Object> data) {

        String mobileNo=(String) data.get("mobileNo");
        BigDecimal amount=new BigDecimal(data.get("amount").toString());

        if(walletRepo.existsByMobileNumber(mobileNo)){
            Wallet wallet=walletRepo.findByMobileNumber(mobileNo);
            BigDecimal balance=wallet.getBankBalance();
            BigDecimal newBalance=balance.add(amount);
            wallet.setBankBalance(newBalance);
            walletRepo.save(wallet);
            kafkaTemplate.send("wallet", "Money successfully added to the wallet. New balance is: " + newBalance + ".");
            return newBalance;
        }else return BigDecimal.ZERO;
    }


    public ResponseEntity<?> moneyTransfer(Map<String, Object> transactionData) {

        String payee_mobileNo = (String) transactionData.get("payee_mobileNumber");
        String payer_mobileNo = (String) transactionData.get("payer_mobileNumber");
        BigDecimal amount=new BigDecimal(transactionData.get("amount").toString());

        String regexp = "\\d{10}";
        if (!payee_mobileNo.matches(regexp)) {
            return new ResponseEntity<>("Invalid payee mobile no.", HttpStatus.BAD_REQUEST);
        }
        if (!payer_mobileNo.matches(regexp)) {
            return new ResponseEntity<>("Invalid payer mobile no.", HttpStatus.BAD_REQUEST);
        }
        if (!walletRepo.existsByMobileNumber(payee_mobileNo)) {
            return new ResponseEntity<>("Wallet not exists with requested payee_mobileNo.", HttpStatus.NOT_FOUND);
        }
        if (!walletRepo.existsByMobileNumber(payer_mobileNo)) {
            return new ResponseEntity<>("Wallet not exists with requested payer_mobileNo.", HttpStatus.NOT_FOUND);
        }
        Wallet payerWallet = walletRepo.findByMobileNumber(payer_mobileNo);
        BigDecimal payerBalance = new BigDecimal(payerWallet.getBankBalance().toString());
        Wallet payeeWallet = walletRepo.findByMobileNumber(payee_mobileNo);
        if (payerBalance.compareTo(amount)<0) {
            Transaction transaction = methods.failTransaction(payerWallet.getId(), payeeWallet.getId(), amount);
            transactionRepo.save(transaction);
            kafkaTemplate.send("wallet", "Transaction, Failure: "+ String.valueOf(transaction));
            return new ResponseEntity<>("Payer doesn't have sufficient amount.", HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = methods.successfulTransaction(payerWallet.getId(), payeeWallet.getId(), amount);
        transactionRepo.save(transaction);
        kafkaTemplate.send("wallet", "Transaction, Success: "+ String.valueOf(transaction));

        BigDecimal newPayerBalance = payerBalance.subtract(amount);
        payerWallet.setBankBalance(newPayerBalance);
        walletRepo.save(payerWallet);

        BigDecimal payeeBalance = payeeWallet.getBankBalance();
        BigDecimal newPayeeBalance = payeeBalance.add(amount);
        payeeWallet.setBankBalance(newPayeeBalance);
        walletRepo.save(payeeWallet);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Transaction> getTransactionsByUser(Map<String, Object> data) {

        String userid=(String) data.get("id");

        List<Transaction> transactionList = new ArrayList<>();
        List<Transaction> payeeTransactions = transactionRepo.getTransactionsByPayeeId(userid);
        transactionList.addAll(payeeTransactions);
        List<Transaction> payerTransactions = transactionRepo.getTransactionsByPayerId(userid);
        transactionList.addAll(payerTransactions);
        kafkaTemplate.send("wallet", "Transactions by user having id "+ userid +" are "+ String.valueOf(transactionList));
        return transactionList;
    }

    public String transactionStatus(String id) {
        if(!transactionRepo.existsById(id)){
            return "Transaction not exists with requested id.";
        }
        Optional<Transaction> transaction =transactionRepo.findById(id);
        String status=transaction.get().getStatus();
        kafkaTemplate.send("wallet", "Transactions status of TransactionId "+ id +" is "+ status);
        return status;
    }


}
