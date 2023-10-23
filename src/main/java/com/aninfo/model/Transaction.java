package com.aninfo.model;
import javax.persistence.*;

@Entity
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransaction;
    private Long cbuAccount;
    private Character type;
    private Double sum;

    public Transaction() {

    }

    public Transaction(Character type, Long cbuAccount, Double sum) {
        this.type = type;
        this.cbuAccount = cbuAccount;
        this.sum = sum;
    }

    public Long getIdTransaction() {
        return idTransaction;
    }

    //SUM
    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    //TYPE
    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    //CBU ACCOUNT

    public Long getCbuAccount() {
        return cbuAccount;
    }

    public void setCbuAccount(Long cbuAccount) {
        this.cbuAccount = cbuAccount;
    }


}

