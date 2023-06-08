package com.example.Mile2.Service;

import com.example.Mile2.Entity.Transaction;
import com.example.Mile2.Entity.Wallet;
import com.example.Mile2.Functions;
import com.example.Mile2.Repository.TransactionRepo;
import com.example.Mile2.Repository.WalletRepo;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class WalletServiceTest {
    @MockBean
    private WalletRepo walletRepo;
    @MockBean
    private TransactionRepo transactionRepo;
    @Autowired
    private WalletService walletService;
    @Autowired
    private Functions functions;


    @Test
    void createWallet() {
        Wallet wallet = functions.getWallet();
        when(walletRepo.save(wallet)).thenReturn(wallet);
        assertThat(walletService.createWallet(wallet)).isEqualTo(wallet);
    }

    @Test
    void addInWallet() {
        Map<String, Object> object=functions.getWalletObject();
        String mobileNo=(String) object.get("mobileNo");
        BigDecimal balance=new BigDecimal(object.get("amount").toString());
        Wallet wallet = functions.getWallet();
        when(walletRepo.existsByMobileNumber(mobileNo)).thenReturn(true);
        when(walletRepo.save(wallet)).thenReturn(wallet);
        when(walletRepo.findByMobileNumber(mobileNo)).thenReturn(wallet);
        BigDecimal currentAmount = wallet.getBankBalance();
        BigDecimal newAmount = currentAmount.add(balance);
        assertThat(walletService.addInWallet(object)).isEqualTo(newAmount);
    }

    @Test
    void moneyTransfer() {

        Map<String, Object> object=functions.getTransactionObject();
        Wallet payeeWallet=functions.getProperWallet1();
        Wallet payerWallet=functions.getProperWallet2();
        String payee_mobileNo = (String) object.get("payee_mobileNumber");
        String payer_mobileNo = (String) object.get("payer_mobileNumber");
        BigDecimal balance=new BigDecimal(object.get("amount").toString());

        when(walletRepo.existsByMobileNumber(payee_mobileNo)).thenReturn(true);
        when(walletRepo.existsByMobileNumber(payer_mobileNo)).thenReturn(true);

        when(walletRepo.findByMobileNumber(payee_mobileNo)).thenReturn(payeeWallet);
        when(walletRepo.findByMobileNumber(payer_mobileNo)).thenReturn(payerWallet);

        ResponseEntity<?> expected=new ResponseEntity<>(HttpStatus.OK);
        assertThat(walletService.moneyTransfer(object)).isEqualTo(expected);

    }

    @Test
    void getTransactionsByUser() {

        Map<String, Object> object=functions.getIdObject();
        List<Transaction> list=new ArrayList<>();
        List<Transaction> payee1=functions.transaction1();
        List<Transaction> payee2=functions.transaction2();
        when(transactionRepo.getTransactionsByPayeeId("1")).thenReturn(payee1);
        list.addAll(payee1);
        when(transactionRepo.getTransactionsByPayerId("1")).thenReturn(payee2);
        list.addAll(payee2);
        assertThat(walletService.getTransactionsByUser(object)).isEqualTo(list);

    }

    @Test
    void transactionStatus() {

        String id="1";
        Transaction transaction=functions.getTransaction();
        when(transactionRepo.existsById(id)).thenReturn(true);
        when(transactionRepo.findById(id)).thenReturn(Optional.ofNullable(transaction));
        assert transaction != null;
        String status=transaction.getStatus();
        assertThat(walletService.transactionStatus(id)).isEqualTo(status);
    }


}