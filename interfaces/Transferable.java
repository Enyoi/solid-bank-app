package interfaces;

import model.Account;

public interface Transferable {
    void transferTo(Account destination, double amount);
}