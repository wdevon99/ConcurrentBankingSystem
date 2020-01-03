/** *************************************
 * File: Student.java
 * Author: Devon Wijesinghe
 *************************************** */


package com.devon.bankingsystem;

import java.util.*;

/**
 * Student Class
 * NOTE: Using "implements Runnable" interface to create a threads is the best approach because NO significant change is made to the thread class,
 * but since the spec states to use "extends Thread", it is used.
 */
public class Student extends Thread {
    private CurrentAccount currentAccount;
    private String name;

    // Student constructor
    public Student(CurrentAccount account, String name, ThreadGroup group) {
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
        System.out.println("<============> STUDENT TRANSACTIONS STARTED <============>");

        List<Transaction> allTransactions = this.getAllTransactions();

        allTransactions.forEach(transaction -> {
            // checking the transaction type and performing either withdrawal or deposit
            if (transaction.getTransactionType() == TransactionType.WITHDRAW) {

                synchronized (this.currentAccount) {
                    currentAccount.withdrawal(transaction);
                    System.out.println("STUDENT -> Transaction performed: " + transaction);
                }
            } else {
                synchronized (this.currentAccount) {
                    currentAccount.deposit(transaction);
                    System.out.println("STUDENT -> Transaction performed: " + transaction);
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

        System.out.println("x============x STUDENT TRANSACTIONS TERMINATED x============x");

        // printing statement after student completes all his/her transactions
        currentAccount.printStatement();
    }

    // Method which creates and return a list of withdraw transactions
    private List <Transaction> getWithdrawTransactions() {
        // Creating withdrawal transactions
        Transaction buyPhone = new Transaction(this.name, 20000, TransactionType.WITHDRAW);
        Transaction buyFood = new Transaction(this.name, 500, TransactionType.WITHDRAW);
        Transaction payRent = new Transaction(this.name, 10000, TransactionType.WITHDRAW);
        Transaction payGymMembership = new Transaction(this.name, 6000, TransactionType.WITHDRAW);

        // Adding withdraw transactions to an ArrayList
        List<Transaction> withdrawTransactions = new ArrayList<>();
        withdrawTransactions.add(buyPhone);
        withdrawTransactions.add(buyFood);
        withdrawTransactions.add(payRent);
        withdrawTransactions.add(payGymMembership);

        return withdrawTransactions;
    }

    // Method which creates and return a list of deposit transactions
    private List <Transaction> getDepositTransactions() {
        // Creating deposit transactions
        Transaction winLottery = new Transaction(this.name, 40000, TransactionType.DEPOSIT);
        Transaction getFreelancePayment = new Transaction(this.name, 5000, TransactionType.DEPOSIT);

        // Adding deposit transactions to an ArrayList
        List<Transaction> depositTransactions = new ArrayList<>();
        depositTransactions.add(winLottery);
        depositTransactions.add(getFreelancePayment);

        return depositTransactions;
    }

    // Method which combines and return a list of all transactions
    private List <Transaction> getAllTransactions() {
        List <Transaction> withdrawTransactions = this.getWithdrawTransactions();
        List <Transaction> depositTransactions = this.getDepositTransactions();

        // Combining withdraw and deposit transactions
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(withdrawTransactions);
        allTransactions.addAll(depositTransactions);

        // Shuffling the transactions
        Collections.shuffle(allTransactions);

        return allTransactions;
    }

}
