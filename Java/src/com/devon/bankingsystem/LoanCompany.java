/** *************************************
 * File: LoanCompany.java
 * Author: Devon Wijesinghe
 *************************************** */
package com.devon.bankingsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * LoanCompany Class
 * NOTE: Using "implements Runnable" interface to create a threads is the best approach because NO significant change is made to the thread class,
 * but since the spec states to use "extends Thread", it is used.
 */
public class LoanCompany extends Thread{
    private CurrentAccount currentAccount;
    private String name;

    // LoanCompany constructor
    public LoanCompany(CurrentAccount account, String name, ThreadGroup group) {
        super(group, name);
        this.currentAccount = account;
        this.name = name;
    }

    @Override
    public void run() {
        this.performTransactions();
    }

    //  Method which executes the transactions
    private void performTransactions() {
        System.out.println("<============> LOAN_COMPANY TRANSACTIONS STARTED <============>");

        List<Transaction> depositTransactions = this.getDepositTransactions();

        depositTransactions.forEach(transaction -> {
            if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
                synchronized (this.currentAccount) {
                    currentAccount.deposit(transaction);
                    System.out.println("LOAN_COMPANY -> Transaction performed: " + transaction);
                }
            }

            // Sleeping for a random amount of time between each transaction.
            try {
                sleep((int)(Math.random() * 300) ) ;
            }
            catch (InterruptedException e) {
                System.out.println("ERROR -> Error while sleeping!");
            }
        });

        System.out.println("x============x LOAN_COMPANY TRANSACTIONS TERMINATED x============x");
    }

    // Method which creates and return a list of deposit transactions
    private List <Transaction> getDepositTransactions() {
        // Creating deposit transactions
        Transaction studentLoanOne = new Transaction(this.name, 50000, TransactionType.DEPOSIT);
        Transaction studentLoanTwo = new Transaction(this.name, 50000, TransactionType.DEPOSIT);
        Transaction studentLoanThree = new Transaction(this.name, 50000, TransactionType.DEPOSIT);

        // Adding deposit transactions to an ArrayList
        List<Transaction> depositTransactions = new ArrayList<>();
        depositTransactions.add(studentLoanOne);
        depositTransactions.add(studentLoanTwo);
        depositTransactions.add(studentLoanThree);

        return depositTransactions;
    }
}
