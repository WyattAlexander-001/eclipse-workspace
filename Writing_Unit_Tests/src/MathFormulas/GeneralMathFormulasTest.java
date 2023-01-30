package MathFormulas;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GeneralMathFormulasTest {
	GeneralMathFormulas example;
	@Before
	public void setUp() throws Exception {
		example = new GeneralMathFormulas(5,10);
	}

	@Test //Answer is 15 because 5+10= 15
	public void addingTest1() {
		Assert.assertEquals(15, example.add());
	}
	
	
	@Test // Failed on purpose. Answer is 15 because 5+10= 15
	public void addingTest2() {
		Assert.assertEquals(80085, example.add());
	}
	
	@Test //Answer is -5 because 5-10= -5
	public void subtractTest1() {
		Assert.assertEquals(-5, example.subtract());
	}
	
	@Test
	public void multiplyTest1() {
		Assert.assertEquals(50, example.multiply());
	}
	
	@Test
	public void divisionTest1() {
		Assert.assertEquals(0, example.divide());
	}
	
	@Test
	public void modulus() {
		Assert.assertEquals(5, example.modulus());
	}
	
	//Ask in class about this
	@Test
	public void power() {
		Assert.assertEquals(64, example.power());
	}
	

}
