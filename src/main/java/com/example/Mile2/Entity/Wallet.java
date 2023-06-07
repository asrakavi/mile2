package com.example.Mile2.Entity;

import jakarta.persistence.*;
import org.intellij.lang.annotations.JdkConstants;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;

//import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 *
 */
@Entity
@Table(name="Wallet")
public class Wallet {

    @Id
    @Column(unique = true)
    private String id;
    @Pattern(value = "^[0-9]{10}$")
    private String mobileNumber;
    private BigDecimal bankBalance;

    public Wallet(String id, String  mobileNumber, BigDecimal bankBalance) {
        super();
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.bankBalance = bankBalance;
    }

    public Wallet() {
        super();
        this.bankBalance = BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber( String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id='" + id + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", bankBalance=" + bankBalance +
                '}';
    }

}