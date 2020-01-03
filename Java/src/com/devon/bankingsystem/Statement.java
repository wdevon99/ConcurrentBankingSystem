/** *************************************
 * File: Statement.java
 * Author: Devon Wijesinghe & P. Howells
 *************************************** */

package com.devon.bankingsystem;

public class  Statement {
	// Private Instance Variables
	private final char TAB = '\t';
	private final int MAX_TRANS = 20;
	private final StatementEntry[] statement = new StatementEntry[ MAX_TRANS ];
	private final String accountHolder;
	private final int accountNumber;
	private int transactionCount = 0;

	// Public Constructor Method
	public Statement (String accountHolder, int accountNumber) {
		this.accountHolder = accountHolder;
		this.accountNumber = accountNumber;
	}

	// Public Modifier Methods
	public void addTransaction(String CID, int amount, int balance, TransactionType transactionType) {
		// Create a new statement entry & add to the statement
		statement[ transactionCount ] = new StatementEntry( CID, amount, balance, transactionType );
		transactionCount++;
	}

	public void print () {
		System.out.println( ) ;
		System.out.println( "Statement for " + accountHolder + "'s Account: " + accountNumber ) ;
		System.out.println( "==============================================================" ) ;
		System.out.format("%1$-18s %2$13s %3$13s %4$13s", "Customer", "Type",  "Amount", "Balance") ;
		System.out.println() ;
		System.out.println( "==============================================================" ) ;

		for (int tid = 0 ; tid < transactionCount ; tid++ ) {
			System.out.format("%1$-18s %2$13s %3$13s %4$13s",
					statement[tid].getCustomer(),
					statement[tid].getTransactionType(),
					this.getTypeSymbol(statement[tid].getTransactionType()) + " " + statement[tid].getAmount(),
					statement[tid].getCurrentBalance());
			System.out.println() ;
		}

		System.out.println( "==============================================================" ) ;
		System.out.println( ) ;
	}

	// Get + or - based on the transaction type
	private String getTypeSymbol(TransactionType transactionType) {
		if (transactionType == TransactionType.WITHDRAW) {
			return "-";
		}
		return "+";
	}
}

