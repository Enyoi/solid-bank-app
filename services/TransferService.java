package services;

import model.Account;

public class TransferService {
    public void transfer(Account from, Account to, double amount) {
        from.transferTo(to, amount);
    }
    
}
