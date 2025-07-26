package model;

import java.util.ArrayList;
import java.util.List;

import interfaces.Printable;
import interfaces.Transferable;

public class Account implements Transferable, Printable {
    protected String number;
    protected double balance;
    protected List<Transaction> history = new ArrayList<>();

    public Account(String number) {
        this.number = number;
        this.balance = 0.0;
    }

    public String getNumber() {
        return number;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public List<Transaction> getHistory() {
        return history;
    }

    public void deposit(double amount) {
       balance += amount;
    }

    public boolean withdraw(double amount) {
       if(amount > balance) return false;
       balance -= amount;
       return true;
    }

    public void addTransaction(Transaction transaction) {
        history.add(transaction);
    }

    @Override
    public void transferTo(Account target, double amount) {
        if(withdraw(amount)){
            target.deposit(amount);
            Transaction transaction = new Transaction(this.number, target.getNumber(), amount);
            this.addTransaction(transaction);
            target.addTransaction(transaction);
        } else {
            System.out.println("Transfer failed: Insufficient funds.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Account Number: " + number);
        System.out.println("Balance: " + balance);
        System.out.println("Transaction History:");
        for (Transaction transaction : history) {
            System.out.println(transaction); 
        }
    }
}
