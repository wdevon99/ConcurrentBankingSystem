/** *************************************
 * File: BankAccount.java
 * Author: Devon Wijesinghe & P. Howells
 *************************************** */
package com.devon.bankingsystem;

public interface BankAccount
{
    // returns the current balance
    int getBalance() ;

    // returns the Account number
    int getAccountNumber() ;

    // returns the Account holder
    String getAccountHolder() ;

    // perform a deposit transaction on the bank account
    void deposit(Transaction t) ;

    // perform a withdrawal transaction on the bank account
    void withdrawal(Transaction t) ;

    // returns true if overdrawn; false otherwise
    boolean isOverdrawn() ;

    // prints out the transactions performed so far
    void printStatement() ;
}