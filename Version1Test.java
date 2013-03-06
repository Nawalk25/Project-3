import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class Version1Test {
	private static final int TIMEOUT = 2000; // 2000 ms = 2 s
	Version1 processor;
	Rectangle rec;
	
	@Before
	public void setUp() throws Exception {
		CensusData data = new CensusData();
		data.add(10,19,-5); 
		data.add(500, 20, -24); 
		data.add(109, 15, -19);
		data.add(144, 22, -23);
		data.add(33, 30, -9); 
		data.add(28, 33, -5); 
		data.add(97, 5, -35); 
		data.add(468, 26, -19); 
		data.add(33, 35, -18); 
		processor = new Version1(data);
	}
	
	private float mercatorConversion(float lat){
		float latpi = (float)(lat * Math.PI / 180);
		float x = (float)Math.log(Math.tan(latpi) + 1 / Math.cos(latpi));
		//System.out.println(lat + " -> " + x);
		return x;
	}
	
	@Test(timeout = TIMEOUT)
	public void testSizeProcessor(){
		assertEquals("Passed a data to processor and checked its size", 
				processor.size, 9);
	}
	
	@Test(timeout = TIMEOUT)
	public void testUSCorners(){
		rec = processor.findUSCorners();
		Rectangle temp = new Rectangle(-35,-5,mercatorConversion(35),mercatorConversion(5));
		assertEquals("Passed a data to processor and checked its corner",
				rec.toString(), temp.toString());
	}
	
	@Test(timeout = TIMEOUT)
	public void calculatePopulation(){
		assertEquals("Checking population in a certain query rectangle", 
				processor.calculateGrid(rec, 6,6,1,1,6,6), 1422);
	}
	
	
	
}
