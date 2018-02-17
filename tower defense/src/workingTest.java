import static org.junit.Assert.*;
import java.io.File;

import org.junit.Test;
import org.junit.Assert;



public class workingTest {
	
	@Test
	public void test() {
		Room room = new Room();
		int x = room.test_a();
		System.out.println(x);
		Assert.assertEquals(96,x); // funkcja sprawdzaj¹ca poprawn¹ iloœæ bloków (8*12)
	}

}
