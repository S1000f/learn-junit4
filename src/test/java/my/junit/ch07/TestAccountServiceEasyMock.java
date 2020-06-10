package my.junit.ch07;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class TestAccountServiceEasyMock {

    private AccountManager mockAccountManager;

    @Before
    public void setUp() {
        mockAccountManager = createMock("mockAccountManager", AccountManager.class);
    }

    @Test
    public void testTransferOk() {
        // given
        Account sender = new Account("1", 200);
        Account beneficiary = new Account("2", 100);

        mockAccountManager.updateAccount(sender);
        mockAccountManager.updateAccount(beneficiary);
        expect(mockAccountManager.findAccountForUser("1")).andReturn(sender);
        expect(mockAccountManager.findAccountForUser("2")).andReturn(beneficiary);

        replay(mockAccountManager);

        AccountService accountService = new AccountService();
        accountService.setAccountManager(mockAccountManager);

        // when
        accountService.transfer("1", "2", 50);

        // then
        assertEquals(150, sender.getBalance());
        assertEquals(150, beneficiary.getBalance());
    }

    @After
    public void tearDown() {
        verify(mockAccountManager);
    }
}
