/** *************************************
 * File: University.java
 * Author: Devon Wijesinghe
 *************************************** */

package com.devon.bankingsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * University Class
 * NOTE: Using "implements Runnable" interface to create a threads is the best approach because NO significant change is made to the thread class,
 * but since the spec states to use "extends Thread", it is used.
 */
public class University extends Thread {
    private CurrentAccount currentAccount;
    private String name;

    public University (CurrentAccount account, String name, ThreadGroup group) {
        super(group, name);
        this.currentAccount = account;
        this.name = name;
    }

    @Override
    public void run() {
        this.performTransactions();
    }

    // Method which executes the transactions
    private void performTransactions() {
        System.out.println("<============> UNIVERSITY TRANSACTIONS STARTED <============>");

        List<Transaction> depositTransactions = this.getWithdrawTransactions();

        depositTransactions.forEach(transaction -> {
            if (transaction.getTransactionType() == TransactionType.WITHDRAW) {
                currentAccount.withdrawal(transaction);
                System.out.println("UNIVERSITY -> Transaction performed: " + transaction);
            }

            // Sleeping for a random amount of time between each transaction.
            try {
                sleep((int)(Math.random() * 300) ) ;
            }
            catch (InterruptedException e) {
                System.out.println("ERROR -> Error while sleeping!");
            }
        });

        System.out.println("x============x UNIVERSITY TRANSACTIONS TERMINATED x============x ");
    }

    // Method which creates and return a list of withdraw transactions
    private List <Transaction> getWithdrawTransactions() {
        // Creating withdraw transactions
        Transaction courseFeeOne = new Transaction(this.name, 40000, TransactionType.WITHDRAW);
        Transaction courseFeeTwo = new Transaction(this.name, 40000, TransactionType.WITHDRAW);
        Transaction courseFeeThree = new Transaction(this.name, 40000, TransactionType.WITHDRAW);

        // Adding withdraw transactions to an ArrayList
        List<Transaction> withdrawTransactions = new ArrayList<>();
        withdrawTransactions.add(courseFeeOne);
        withdrawTransactions.add(courseFeeTwo);
        withdrawTransactions.add(courseFeeThree);

        return withdrawTransactions;
    }
}
