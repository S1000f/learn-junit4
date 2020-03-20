package scratch;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.number.IsCloseTo.*;

import org.junit.Ignore;
import org.junit.Test;

public class AssertHamcrestTest {
	
	@Ignore
	@Test
	public void doubleTypeTest() {
		
		assertThat(2.32 * 3, equalTo(6.96));
	}
	
	// not best idea
	@Test
	public void doubleTypeTestUsingMath() {
		assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
	}
	
	// imported additional hamcrest matcher; number.IsCloseTo.isClose()
	@Test
	public void doubleTypeTestUsingIsClose() {
		
		assertThat(2.32 * 3, closeTo(6.96, 0.5));
	}
}





