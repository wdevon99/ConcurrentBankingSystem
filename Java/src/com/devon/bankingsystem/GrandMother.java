/** *************************************
 * File: GrandMother.java
 * Author: Devon Wijesinghe
 *************************************** */
package com.devon.bankingsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * GrandMother Class
 * NOTE: Using "implements Runnable" interface to create a threads is the best approach because NO significant change is made to the thread class,
 * but since the spec states to use "extends Thread", it is used.
 */
public class GrandMother extends Thread {
    private CurrentAccount currentAccount;
    private String name;

    // GrandMother constructor
    public GrandMother(CurrentAccount account, String name, ThreadGroup group) {
        super(group, name);
        this.currentAccount = account;
        this.name = name;
    }

    @Override
    public void run() {
        this.performTransactions();
    }

    private void performTransactions() {
        System.out.println("<============> GRAND_MOTHER TRANSACTIONS STARTED <============>");

        List<Transaction> depositTransactions = this.getDepositTransactions();

        depositTransactions.forEach(transaction -> {
            if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
                synchronized (this.currentAccount) {
                    currentAccount.deposit(transaction);
                    System.out.println("GRAND_MOTHER -> Transaction performed: " + transaction);
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

        System.out.println("x============x GRAND_MOTHER TRANSACTIONS TERMINATED x============x");
    }

    private List <Transaction> getDepositTransactions() {
        // Creating deposit transactions
        Transaction christmasGift = new Transaction(this.name, 1000, TransactionType.DEPOSIT);
        Transaction birthdayGift= new Transaction(this.name, 1000, TransactionType.DEPOSIT);

        // Adding deposit transactions to an ArrayList
        List<Transaction> depositTransactions = new ArrayList<>();
        depositTransactions.add(christmasGift);
        depositTransactions.add(birthdayGift);

        return depositTransactions;
    }
}
