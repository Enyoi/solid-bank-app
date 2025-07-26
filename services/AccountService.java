package services;

import model.Account;

public class AccountService {
    public void printHistory(Account account) {
        account.getHistory().forEach(System.out::println);
    }
}
