package scratch;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
       super(message);
    }

    private static final long serialVersionUID = 1L;
 }

class Account {
    int balance;
    String name;

    Account(String name) {
       this.name = name;
    }

    void deposit(int dollars) {
       balance += dollars;
    }

    void withdraw(int dollars) {
       if (balance < dollars) {
          throw new InsufficientFundsException("balance only " + balance);
       }
       balance -= dollars;
    }

    public String getName() {
       return name;
    }

    public int getBalance() {
       return balance;
    }

    public boolean hasPositiveBalance() {
       return balance > 0;
    }
 }

 class Customer {
    List<Account> accounts = new ArrayList<>();

    void add(Account account) {
       accounts.add(account);
    }

    Iterator<Account> getAccounts() {
       return accounts.iterator();
    }
 }


public class AssertTest {
	private Account account;
	
	@Before
	public void createAccount() {
		account = new Account("an account name");
	}
	
	
	@Test
	public void hasPositiveBal() {
		account.deposit(50);
		assertTrue(account.hasPositiveBalance());
	}
	
	@Test
	public void depositIncreaseBal() {
		int initBalance = account.getBalance();
		account.deposit(100);
		assertTrue(account.getBalance() > initBalance);
		
		assertThat(account.getBalance(), equalTo(100));
		
		assertThat(account.getBalance() > 0, is(true));
	}
	
	@Test
	public void usingStartsWith() {
		assertThat(account.getName(), startsWith("an"));
	}
	
	@Ignore
	@Test
	public void javaArrayAndCollectionTest() {
		
		assertThat(new String[] {"a","b","c"}, equalTo(new String[] {"a","b"}));
		assertThat(Arrays.asList("1","2","3"), equalTo(Arrays.asList("1", "2")));
	}
	
	@Test
	public void isIsJustDecorator() {
		Account account = new Account("milly the cat");
		
		assertThat(account.getName(), is(equalTo("milly the cat")));
		assertThat(account.getName(), is("milly the cat"));
		assertThat(account.getName(), equalTo("milly the cat"));
	}
	
	@Test
	public void dontNullIt() {
		Account accountNull = new Account(null);
		assertThat(accountNull.getName(), is(nullValue()));
		
		assertThat(account.getName(), is(notNullValue()));
	}
	
	@Test
	public void TestWithWorthlessComment() {
		account.deposit(50);
		assertThat("account has 100, maybe", account.getBalance(), equalTo(50));
	}
	
	@Test(expected = InsufficientFundsException.class)
	public void exceptionTest() {
		account.withdraw(200);
	}
	
	@Test
	public void exceptionTestUsingTrycatch() {
		
		try {
			account.withdraw(100);
			fail();
			
		} catch (InsufficientFundsException intended) {
			assertThat(intended.getMessage(), equalTo("balance only 0"));
		}
	}
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@Test
	public void exceptionRule() {
		ex.expect(InsufficientFundsException.class);
		ex.expectMessage("balance only 0");
		
		account.withdraw(3000);
	}

}






