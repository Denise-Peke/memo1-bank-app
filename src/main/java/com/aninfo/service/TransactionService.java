package com.aninfo.service;


import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;

    public Collection<Transaction>findByCbuAccount(Long cbu) {
        return  transactionRepository.findTransactionByCbuAccount(cbu);
    }



    public Transaction createTransaction(Transaction transaction) {
        long cbu = transaction.getCbuAccount();
        double sum = transaction.getSum();
        char type = transaction.getType();

        if( !( type == 'D' || type=='W' ) ) {
            throw new InvalidTransactionTypeException("Transaction type must be D or W");
        }

        if (type == 'D') {
            accountService.deposit(cbu, sum);
        }

        if (type =='W'){
            accountService.withdraw(cbu,sum);
        }

        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransaction() {
        return transactionRepository.findAll();
    }

    public void deleteByIdTransaction(Long idTransaction) {
        Transaction transaction = transactionRepository.findTransactionByIdTransaction(idTransaction);
        if ( transaction.getType() == 'D' ){
            accountService.withdraw(transaction.getCbuAccount(), transaction.getSum());
        } else {
            accountService.deposit(transaction.getCbuAccount(), transaction.getSum());
        }
        transactionRepository.deleteById(idTransaction);
    }

    public Optional<Transaction> findByIdTransaction(Long idTransaction) {
        return Optional.ofNullable(transactionRepository.findTransactionByIdTransaction(idTransaction));
    }

}
