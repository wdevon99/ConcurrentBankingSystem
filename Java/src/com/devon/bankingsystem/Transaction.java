/** *************************************
 * File: Transaction.java
 * Author: Devon Wijesinghe & P. Howells
 *************************************** */

package com.devon.bankingsystem;

public class Transaction {
    private final String customerID;
    private final int amount ;
    private final TransactionType transactionType;

    public Transaction(String CID, int amount, TransactionType transactionType) {
        this.customerID = CID ;
        this.amount = amount ;
        this.transactionType = transactionType;
    }

    public String getCID() {
        return customerID;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String toString() {
        return  new String( "[ " + "Customer: " + customerID + ", " + "Amount: "   + amount + ", " + "Transaction Type: "   + transactionType + " ]"  ) ;
    }
}
