package model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String name;
    private final List<Account> accounts = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    } 
    
    public Account findAccount(String number) {
        return accounts.stream()
                .filter(account -> account.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }
}
