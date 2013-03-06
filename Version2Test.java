import static org.junit.Assert.*;

import org.junit.Test;


public class Version2Test {

	@Test
	public void testFindCorners() {
		Version2 ver = new Version2(PopulationQuery.parse("simpleData.txt"));
		assertEquals("The corners should be","[left=0.0 right=9.0 top=0.17542583 bottom=0.017454179]",ver.findUSCorners().toString());
	}

	@Test
	public void testCalculateGridAll() {
		Version2 ver = new Version2(PopulationQuery.parse("simpleData.txt"));
		Rectangle temp = ver.findUSCorners();
		int allPopulation = ver.calculateGrid(temp, 10, 10, 1, 1, 10, 10);
		assertEquals("The population should be",100,allPopulation);
	}
	
	@Test
	public void testCalculateSmallGridBorder() {
		Version2 ver = new Version2(PopulationQuery.parse("simpleData.txt"));
		Rectangle temp = ver.findUSCorners();
		int allPopulation = ver.calculateGrid(temp, 10, 10, 1, 1, 5, 5);
		assertEquals("The population should be",25,allPopulation);
	}
	
	@Test
	public void testCalculateSmallGrid() {
		Version2 ver = new Version2(PopulationQuery.parse("simpleData.txt"));
		Rectangle temp = ver.findUSCorners();
		int allPopulation = ver.calculateGrid(temp, 20, 20, 2, 2, 5, 5);
		assertEquals("The population should be",4,allPopulation);
	}
	
}
