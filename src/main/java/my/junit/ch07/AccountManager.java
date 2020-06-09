package my.junit.ch07;

public interface AccountManager {
    Account findAccountForUser(String userId);
    void updateAccount(Account account);
}
