/** *************************************
 * File: CurrentAccount.java
 * Author: Devon Wijesinghe
 *************************************** */
package com.devon.bankingsystem;

public class CurrentAccount implements BankAccount {
    // Instance variables of CurrentAccount
    private String customerID;
    private int accountNumber;
    private Statement statement;
    // "balance" variable is ths shared resource - access tho this should be mutually exclusive
    private volatile int balance; // NOTE: "volatile" keyword is used so that the value of this variable will never be cached thread-locally

    // CurrentAccount constructor
    public CurrentAccount(String customerID, int accountNumber, int balance) {
        this.customerID = customerID;
        this.accountNumber = accountNumber;
        this.statement = new Statement(customerID, accountNumber);
        this.balance = balance;
    }

    /**
     * ===============================================================================================================================
     * NOTE: Implementing mutual exclusiveness of "balance" using SEMAPHORES
     * ===============================================================================================================================
     */
    // Method for depositing money to the current account
    @Override
    public void deposit(Transaction transaction) {
        try {
            // trying to acquire the permit of the mutex semaphore
            BankingSystem.mutexSemaphore.acquire();

            int amountToDeposit = transaction.getAmount();
            this.balance += amountToDeposit;
            this.statement.addTransaction(transaction.getCID(), amountToDeposit, this.getBalance(), TransactionType.DEPOSIT);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // releasing the permit of the mutex semaphore
            BankingSystem.mutexSemaphore.release();
        }
    }

    @Override
    // Method for withdrawing money from the current account
    public void withdrawal(Transaction transaction) {
        int amountToWithdraw = transaction.getAmount();

        boolean hasPrintedMessage = false;
        // This while loop makes sure that it wait till the funds are sufficient to withdraw
        while (amountToWithdraw > this.getBalance()) {
            if (!hasPrintedMessage) {
                System.out.println("CURRENT_ACCOUNT -> Failed to Withdraw " + amountToWithdraw + " by " + transaction.getCID() + ". Waiting for balance to be sufficient!");
                hasPrintedMessage = true;
            }
        }

        try {
            // trying to acquire the permit of the mutex semaphore
            BankingSystem.mutexSemaphore.acquire();

            this.balance -= amountToWithdraw;
            this.statement.addTransaction(transaction.getCID(), amountToWithdraw, this.getBalance(), TransactionType.WITHDRAW);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // releasing the permit of the mutex semaphore
            BankingSystem.mutexSemaphore.release();
        }
    }

    /**
     * ===============================================================================================================================
     * NOTE: Implementing mutual exclusiveness of "balance" using synchronized / wait() / notifyAll() methods
     * -> You able to UN-COMMENT the below methods and COMMENT the above methods and run to use this way of achieving mutual exclusiveness
     * ===============================================================================================================================
     */
    /*

    // Synchronized method for depositing money to the current account
    @Override
    public synchronized void deposit(Transaction transaction) {
        int amountToDeposit = transaction.getAmount();
        this.balance += amountToDeposit;
        this.statement.addTransaction(transaction.getCID(), amountToDeposit, this.getBalance(), TransactionType.DEPOSIT);

        // Notifying all the other waiting threads that the deposit is complete
        notifyAll();
    }

    // Synchronized method for withdrawing money from the current account
    @Override
    public synchronized void withdrawal(Transaction transaction) {
        int amountToWithdraw = transaction.getAmount();

        while (amountToWithdraw > this.getBalance()) {
            try {
                System.out.println("CURRENT_ACCOUNT -> Failed to Withdraw " + amountToWithdraw + " by " + transaction.getCID() + ". Waiting for balance to be sufficient!");

                // Waiting until balance is available
                wait();
            } catch (InterruptedException e) {
                System.out.println("ERROR -> Error while performing withdrawal!");
            }
        }

        this.balance -= amountToWithdraw;
        this.statement.addTransaction(transaction.getCID(), amountToWithdraw, this.getBalance(), TransactionType.WITHDRAW);
    }

     */

    @Override
    public boolean isOverdrawn() {
        return this.balance < 0;
    }

    @Override
    public void printStatement() {
        this.statement.print();
    }

    // Getters
    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public int getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public String getAccountHolder() {
        return this.customerID;
    }
}
