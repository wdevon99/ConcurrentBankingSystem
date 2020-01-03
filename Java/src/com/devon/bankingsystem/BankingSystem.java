/** *************************************
 * File: BankingSystem.java
 * Author: Devon Wijesinghe
 *************************************** */
package com.devon.bankingsystem;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class BankingSystem {
    // Student current account
    CurrentAccount studentAccount;
    // Two thread groups : one for humans and one for the Loan Company & University
    ThreadGroup humans;
    ThreadGroup organizations;
    // 1 Student,1 Grandmother,1 Loan Company & 1 University
    Student student;
    GrandMother grandMother;
    LoanCompany loanCompany;
    University university;

    // Mutex Semaphore
    private Semaphore mutexSemaphore = new Semaphore(1);;

    // BankingSystem constructor
    public BankingSystem() {
        this.initializeBankingSystem();
    }

    public static void main (String[] args) {
        // Creating an instance of bankingSystem
        BankingSystem bankingSystem = new BankingSystem();

        // Getting prompt to start banking system
        Scanner input = new Scanner(System.in);
        bankingSystem.printWelcomeMessage();
        input.nextLine();
        input.close();

        // Starting the Threads
        bankingSystem.student.start();
        bankingSystem.grandMother.start();
        bankingSystem.loanCompany.start();
        bankingSystem.university.start();

        try {
            // Making the main thread wait until the other threads completes execution
            bankingSystem.student.join();
            bankingSystem.grandMother.join();
            bankingSystem.loanCompany.join();
            bankingSystem.university.join();
        } catch (InterruptedException e) {
            System.out.println("ERROR -> Error while joining threads!");
        }

        // Printing the final account statement
        bankingSystem.studentAccount.printStatement();
    }

    private void initializeBankingSystem () {
        // Initializing Thread Groups
        this.humans = new ThreadGroup("Humans");
        this.organizations = new ThreadGroup("Organizations");

        // Initializing Student current account
        this.studentAccount = new CurrentAccount("Devon Wijesinghe", 123456789, 0);

        // Initializing 1 Student,1 Grandmother,1 Loan Company & 1University
        this.student = new Student(studentAccount, studentAccount.getAccountHolder(), humans);
        this.grandMother = new GrandMother(studentAccount, "Susila Rajasekara", humans);
        this.loanCompany = new LoanCompany(studentAccount, "Sampath Bank", organizations);
        this.university = new University(studentAccount, "IIT University", organizations);
    }

    private void printWelcomeMessage () {
        System.out.println("\n" +
                "  ____          _   _ _  _______ _   _  _____    _______     _______ _______ ______ __  __ \n" +
                " |  _ \\   /\\   | \\ | | |/ /_   _| \\ | |/ ____|  / ____\\ \\   / / ____|__   __|  ____|  \\/  |\n" +
                " | |_) | /  \\  |  \\| | ' /  | | |  \\| | |  __  | (___  \\ \\_/ / (___    | |  | |__  | \\  / |\n" +
                " |  _ < / /\\ \\ | . ` |  <   | | | . ` | | |_ |  \\___ \\  \\   / \\___ \\   | |  |  __| | |\\/| |\n" +
                " | |_) / ____ \\| |\\  | . \\ _| |_| |\\  | |__| |  ____) |  | |  ____) |  | |  | |____| |  | |\n" +
                " |____/_/    \\_\\_| \\_|_|\\_\\_____|_| \\_|\\_____| |_____/   |_| |_____/   |_|  |______|_|  |_|\n");
        System.out.println(" ========================== Press ENTER to start Banking System =========================== ");
    }
}
