/** *************************************
 * File: Statement.java
 * Author: Devon Wijesinghe & P. Howells
 *************************************** */

package com.devon.bankingsystem;

public class StatementEntry {
    private final char TAB = '\t' ;
    private final String CustomerID ;
    private final int amount ;
    private final int currentBal ;
    private final TransactionType transactionType;

    public StatementEntry(String CID, int amount, int currentBal, TransactionType transactionType) {
        this.CustomerID = CID;
        this.amount = amount;
        this.currentBal = currentBal;
        this.transactionType = transactionType;
    }

    public String getCustomer() { return CustomerID ; }
    public int getAmount() { return amount ; }
    public int getCurrentBalance() { return currentBal ; }
    public TransactionType getTransactionType() { return transactionType ; }

    public String toString( ) {
        return  new String(
                "Customer: " + CustomerID + ","  + TAB +
                "Amount: "   + amount     + ", " + TAB +
                "Balance: "  + currentBal  + ", " + TAB + "Type: "  + transactionType
        ) ;
    }
}
