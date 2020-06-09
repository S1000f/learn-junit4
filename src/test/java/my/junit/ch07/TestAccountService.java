package my.junit.ch07;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAccountService {

    @Test
    public void testTransferOk() {

        // given
        Account sender = new Account("1", 200);
        Account beneficiary = new Account("2", 100);
        MockAccountManager mockAccountManager = new MockAccountManager();
        mockAccountManager.addAccount("1", sender);
        mockAccountManager.addAccount("2", beneficiary);
        AccountService accountService = new AccountService();
        accountService.setAccountManager(mockAccountManager);

        // when
        accountService.transfer("1", "2", 50);

        // then
        assertEquals(150, sender.getBalance());
        assertEquals(150, beneficiary.getBalance());
    }
}
