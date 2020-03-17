package iloveyouboss;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;

public class ScoreCollectionTest {

	@Test
	public void answerArithmeticMeanOfTwoNumbers() {
		// Arrange
		ScoreCollection collection = new ScoreCollection();
		collection.add(()-> 5);
		collection.add(()-> 7);
		
		// Act
		int expected = collection.arithmeticMean();
		
		// Assert
		assertThat(expected, equalTo(6));
	}

}
