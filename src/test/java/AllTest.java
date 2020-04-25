import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = {ParameterizedTest.class, CalculatorTest.class})
public class AllTest {
}
